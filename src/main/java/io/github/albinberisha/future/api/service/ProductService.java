package io.github.albinberisha.future.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.github.albinberisha.future.api.dto.ProductAttributeDto;
import io.github.albinberisha.future.api.dto.ProductCreateRequest;
import io.github.albinberisha.future.api.dto.ProductUpdateRequest;
import io.github.albinberisha.future.api.dto.ProductVariantUpdateRequest;
import io.github.albinberisha.future.api.entity.Merchant;
import io.github.albinberisha.future.api.entity.Product;
import io.github.albinberisha.future.api.entity.ProductAttribute;
import io.github.albinberisha.future.api.entity.ProductCategory;
import io.github.albinberisha.future.api.entity.ProductFilter;
import io.github.albinberisha.future.api.entity.ProductImage;
import io.github.albinberisha.future.api.entity.ProductVariant;
import io.github.albinberisha.future.api.entity.Store;
import io.github.albinberisha.future.api.entity.embeddable.ProductTranslations;
import io.github.albinberisha.future.api.entity.enums.Language;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.repository.ProductRepository;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
@Validated
public class ProductService {
	private static final String PRODUCT_WITH_ALL = "Product.withAll";
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductFilterService productFilterService;
	@Autowired
	private ProductImageService productImageService;
	@Autowired
	private StoreService storeService;

	public List<Product> findAll() {
		return productRepository.findAll(PRODUCT_WITH_ALL);
	}

	public Optional<Product> findById(@NotBlank String id) {
		return productRepository.findById(id, PRODUCT_WITH_ALL);
	}

	public List<Product> findByFilters(List<String> categories) {
		return productRepository.findByFilters(categories);
	}

	@Transactional
	public Product save(@NotNull Merchant merchant, @NotNull @Valid ProductCreateRequest productCreateRequest) {
		Product product = new Product();
		product.setTranslations(Stream.concat(productCreateRequest.getNames().keySet().stream(), productCreateRequest.getDescriptions().keySet().stream()).distinct().map(lng -> {
			ProductTranslations translations = new ProductTranslations();
			translations.setName(productCreateRequest.getNames().get(lng));
			translations.setDescription(productCreateRequest.getDescriptions().get(lng));
			translations.setLanguage(Language.getByCode(lng));
			return translations;
		}).collect(Collectors.toSet()));
		ProductCategory category = productCategoryService.findById(productCreateRequest.getCategoryId())
				.orElseThrow(() -> new ApiException("Product category not found"));
		product.setCategory(category);
		Set<ProductImage> images = productImageService.findByIdIn(productCreateRequest.getImageIds());
//		if (images.size() != productCreateRequest.getImageIds().size()) {
//			throw new ApiException("Some images not found");
//		}
		images.forEach(img -> img.setProduct(product));
		product.setImages(images);
		product.setMerchant(merchant);
		product.setVariants(productCreateRequest.getVariants().stream().map(productVariantDto -> {
			ProductVariant variant = new ProductVariant();
			variant.setProduct(product);
			variant.setPrice(productVariantDto.getPrice());
			variant.setStockQuantity(productVariantDto.getStockQuantity());
			Set<ProductImage> variantImages = productImageService.findByIdIn(productVariantDto.getImageIds());
//			if (variantImages.size() != productVariantDto.getImageIds().size()) {
//				throw new ApiException("Some images not found");
//			}
//			variantImages.forEach(img -> img.setProductVariant(variant));
			variant.setImages(variantImages);
			variant.setAttributes(productVariantDto.getAttributes().stream().map(productAttributeDto -> {
				ProductAttribute attribute = new ProductAttribute();
				attribute.setId(UUID.randomUUID().toString());
				attribute.setProductVariant(variant);
				ProductFilter filter = productFilterService.findById(productAttributeDto.getProductFilterId())
						.orElseThrow(() -> new ApiException("Product filter not found"));
				attribute.setProductFilter(filter);
				attribute.setValue(productAttributeDto.getValue());
				return attribute;
			}).collect(Collectors.toSet()));
			Set<Store> stores = storeService.findByIdIn(productVariantDto.getStoreIds());
			if (stores.size() != productVariantDto.getStoreIds().size())
				throw new ApiException("Some stores not found");
			variant.setStores(stores);
			return variant;
		}).collect(Collectors.toSet()));
		return productRepository.save(product);
	}

	public List<Product> findByMerchant(@NotNull Merchant merchant) {
		return productRepository.findByMerchant(merchant, PRODUCT_WITH_ALL);
	}

	@Transactional
	public void deleteByIdAndMerchant(@NotBlank String id, @NotNull Merchant merchant) {
		productRepository.deleteByIdAndMerchant(id, merchant);
	}

	@Transactional
	public Product update(@NotBlank String id, @NotNull Merchant merchant, @NotNull @Valid ProductUpdateRequest productUpdateRequest) {
		Product product = productRepository.findByIdAndMerchant(id, merchant)
				.orElseThrow(() -> new ApiException("Product not found"));
		Stream.concat(productUpdateRequest.getNames().keySet().stream(), productUpdateRequest.getDescriptions().keySet().stream()).distinct().forEach(lng -> {
			ProductTranslations pt = product.getTranslations().stream()
					.filter(t -> t.getLanguage() == Language.getByCode(lng))
					.findFirst()
					.orElseGet(() -> {
						ProductTranslations newPt = new ProductTranslations();
						newPt.setLanguage(Language.getByCode(lng));
						return newPt;
					});
			pt.setName(productUpdateRequest.getNames().get(lng));
			pt.setDescription(productUpdateRequest.getDescriptions().get(lng));
		});
		if (StringUtils.isNotBlank(productUpdateRequest.getCategoryId())) {
			ProductCategory category = productCategoryService.findById(productUpdateRequest.getCategoryId())
					.orElseThrow(() -> new ApiException("Product category not found"));
			product.setCategory(category);
		}
		Set<ProductImage> images = productImageService.findByIdIn(productUpdateRequest.getImageIds());
//		if (images.size() != productUpdateRequest.getImageIds().size()) {
//			throw new ApiException("Some images not found");
//		}
		images.forEach(img -> img.setProduct(product));
		product.setImages(images);
		if (CollectionUtils.isNotEmpty(productUpdateRequest.getVariants())) {
			List<ProductVariant> combinedVariants = new ArrayList<>();
			for (ProductVariantUpdateRequest productVariantDto : productUpdateRequest.getVariants()) {
				ProductVariant variant = product.getVariants().stream()
						.filter(v -> StringUtils.equals(v.getId(), productVariantDto.getId()))
						.findFirst()
						.orElseGet(() -> {
							ProductVariant newProductVariant = new ProductVariant();
							newProductVariant.setProduct(product);
							return newProductVariant;
						});
				combinedVariants.add(variant);
				variant.setPrice(productVariantDto.getPrice());
				variant.setStockQuantity(productVariantDto.getStockQuantity());
				Set<ProductImage> variantImages = productImageService.findByIdIn(productVariantDto.getImageIds());
//				if (variantImages.size() != productVariantDto.getImageIds().size()) {
//					throw new ApiException("Some images not found");
//				}
				variantImages.forEach(img -> img.setProductVariant(variant));
				variant.setImages(variantImages);
				List<ProductAttribute> combinedAttributes = new ArrayList<>();
				for (ProductAttributeDto productAttributeDto : productVariantDto.getAttributes()) {
					ProductAttribute attribute = variant.getAttributes().stream()
							.filter(a -> StringUtils.equals(a.getId(), productAttributeDto.getId()))
							.findFirst()
							.orElseGet(() -> {
								ProductAttribute newProductAttribute = new ProductAttribute();
								newProductAttribute.setId(UUID.randomUUID().toString());
								newProductAttribute.setProductVariant(variant);
								ProductFilter filter = productFilterService.findById(productAttributeDto.getProductFilterId())
										.orElseThrow(() -> new ApiException("Product filter not found"));
								newProductAttribute.setProductFilter(filter);
								return newProductAttribute;
							});
					attribute.setValue(productAttributeDto.getValue());
					combinedAttributes.add(attribute);
				}
				Set<Store> stores = storeService.findByIdIn(productVariantDto.getStoreIds());
				if (stores.size() != productVariantDto.getStoreIds().size())
					throw new ApiException("Some stores not found");
				variant.setStores(stores);
				variant.getAttributes().clear();
				variant.getAttributes().addAll(combinedAttributes);
			}
			product.getVariants().clear();
			product.getVariants().addAll(combinedVariants);
		}
		return productRepository.save(product);
	}
}
