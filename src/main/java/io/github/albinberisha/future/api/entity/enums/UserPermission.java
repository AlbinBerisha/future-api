package io.github.albinberisha.future.api.entity.enums;

import java.util.List;

import io.jsonwebtoken.lang.Arrays;

/**
 * @author Albin Berisha
 *
 */
public enum UserPermission {
	CREATE_USER(Scope.SYSTEM, Scope.MERCHANT),
	VIEW_USER(Scope.SYSTEM, Scope.MERCHANT),
	UPDATE_USER(Scope.SYSTEM, Scope.MERCHANT),
	DELETE_USER(Scope.SYSTEM, Scope.MERCHANT),

	CREATE_USER_ROLE(Scope.SYSTEM, Scope.MERCHANT),
	VIEW_USER_ROLE(Scope.SYSTEM, Scope.MERCHANT),
	UPDATE_USER_ROLE(Scope.SYSTEM, Scope.MERCHANT),
	DELETE_USER_ROLE(Scope.SYSTEM, Scope.MERCHANT),

	CREATE_MERCHANT(Scope.SYSTEM),
	VIEW_MERCHANT(Scope.SYSTEM),
	UPDATE_MERCHANT(Scope.SYSTEM),
	DELETE_MERCHANT(Scope.SYSTEM),

	CREATE_STORE(Scope.SYSTEM, Scope.MERCHANT),
	VIEW_STORE(Scope.SYSTEM, Scope.MERCHANT),
	UPDATE_STORE(Scope.SYSTEM, Scope.MERCHANT),
	DELETE_STORE(Scope.SYSTEM, Scope.MERCHANT),

	VIEW_STORE_FEATURE(Scope.SYSTEM, Scope.MERCHANT),

	CREATE_PRODUCT(Scope.MERCHANT),
	VIEW_PRODUCT(Scope.SYSTEM, Scope.MERCHANT),
	UPDATE_PRODUCT(Scope.MERCHANT),
	DELETE_PRODUCT(Scope.SYSTEM, Scope.MERCHANT),

	CREATE_PRODUCT_CATEGORY(Scope.SYSTEM),
	VIEW_PRODUCT_CATEGORY(Scope.SYSTEM, Scope.MERCHANT),
	UPDATE_PRODUCT_CATEGORY(Scope.SYSTEM),
	DELETE_PRODUCT_CATEGORY(Scope.SYSTEM),

	CREATE_PRODUCT_FILTER(Scope.SYSTEM);
	private Scope[] scopes;

	private UserPermission(Scope... scopes) {
		this.scopes = scopes;
	}

	public List<Scope> getScopes() {
		return Arrays.asList(scopes);
	}
}
