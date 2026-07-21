package io.github.albinberisha.future.api.util;

import io.github.albinberisha.future.api.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

/**
 * @author Albin Berisha
 *
 */
@Component
public class JwtUtils {
	@Autowired
	private SecretKey secretKey;

	private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60; // 1 hour
	private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7 days

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	}

	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateAccessToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRole().getName());
		return doGenerateToken(claims, user.getUsername(), ACCESS_TOKEN_VALIDITY);
	}

    public String generateRefreshToken(User user) {
        return doGenerateToken(new HashMap<>(), user.getUsername(), REFRESH_TOKEN_VALIDITY);
    }

	private String doGenerateToken(Map<String, Object> claims, String subject, long validity) {
		return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + validity))
				.signWith(secretKey).compact();
	}

	public boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public boolean validateToken(String token, User user) {
		final String username = getUsernameFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}
}
