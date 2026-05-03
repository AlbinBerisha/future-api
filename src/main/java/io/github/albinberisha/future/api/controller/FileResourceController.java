package io.github.albinberisha.future.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.albinberisha.future.api.dto.FileResourceDto;
import io.github.albinberisha.future.api.entity.FileResource;
import io.github.albinberisha.future.api.entity.enums.StorageType;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.FileResourceService;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@RequestMapping("/api/files")
@RestController
public class FileResourceController {
	@Autowired
	private FileResourceService fileResourceService;
	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping
	public ResponseEntity<FileResourceDto> upload(@RequestParam MultipartFile file) {
		FileResource resource = fileResourceService.upload(file);
		return new ResponseEntity<>(objectMapper.toFileResourceDto(resource), HttpStatus.CREATED);
	}

	@GetMapping("/{id}/meta")
	public ResponseEntity<FileResourceDto> getById(@PathVariable String id) {
		FileResource resource = fileResourceService.findById(id)
				.orElseThrow(() -> new RuntimeException("Resource not found"));
		return ResponseEntity.ok(objectMapper.toFileResourceDto(resource));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> download(@PathVariable String id) {
		FileResource resource = fileResourceService.findById(id)
				.orElseThrow(() -> new RuntimeException("File not found"));

		if (resource.getStorageType() == StorageType.S3) {
			String url = fileResourceService.getDownloadUrl(id);
			return ResponseEntity.status(HttpStatus.FOUND)
					.location(URI.create(url))
					.build();
		}

		try {
			InputStream inputStream = fileResourceService.download(id);
			InputStreamResource body = new InputStreamResource(inputStream);
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(
							resource.getContentType() != null ? resource.getContentType() : "application/octet-stream"))
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"inline; filename=\"" + resource.getOriginalFilename() + "\"")
					.body(body);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		fileResourceService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
