package io.github.albinberisha.future.api.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.albinberisha.future.api.dto.AuthenticationRequest;
import io.github.albinberisha.future.api.dto.AuthenticationResponse;
import io.github.albinberisha.future.api.entity.User;
import io.github.albinberisha.future.api.exception.ApiException;
import io.github.albinberisha.future.api.mapper.ObjectMapper;
import io.github.albinberisha.future.api.service.UserService;
import io.github.albinberisha.future.api.util.JwtUtils;

/**
 * @author Albin Berisha
 *
 */
@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/api/auth")
	public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request, HttpServletResponse response) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		User user = (User) authentication.getPrincipal();
		String jwt = jwtUtils.generateAccessToken(user);
		String refreshToken = jwtUtils.generateRefreshToken(user);
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
		refreshTokenCookie.setAttribute("SameSite", "None");
		response.addCookie(refreshTokenCookie);
		AuthenticationResponse responseDto = new AuthenticationResponse();
		responseDto.setJwt(jwt);
		responseDto.setUser(objectMapper.toUserDto(user));
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/api/auth/refresh")
	public ResponseEntity<AuthenticationResponse> refreshToken(@CookieValue(required = false) String refreshToken) {
		if (StringUtils.isBlank(refreshToken) || !jwtUtils.canTokenBeRefreshed(refreshToken))
			throw new ApiException("No active session");
		String username = jwtUtils.getUsernameFromToken(refreshToken);
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new ApiException("User not found"));
		String jwt = jwtUtils.generateAccessToken(user);
		AuthenticationResponse responseDto = new AuthenticationResponse();
		responseDto.setJwt(jwt);
		responseDto.setUser(objectMapper.toUserDto(user));
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/api/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", "");
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setMaxAge(0);
		refreshTokenCookie.setAttribute("SameSite", "None");
		response.addCookie(refreshTokenCookie);
		return ResponseEntity.ok().build();
	}
}
