package net.awsomerecipes.ws.api.rest.config.security;

import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import net.awsomerecipes.ws.api.rest.beans.User;

@Component
public class TokenHelper {

	@Value("${spring.security.oauth2.expiration}")
	private Long EXPIRATION_TIME;
	@Value("${spring.security.oauth2.key}")
	private String KEY;
	@Value("${spring.security.oauth2.header-name}")
	private String AUTH_HEADER;

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	public Long getExpirationTime() {
		return EXPIRATION_TIME;
	}

	public String generateToken(String username) {
		Key key = Keys.hmacShaKeyFor(KEY.getBytes());
		String token = Jwts.builder()
						.setSubject(username)
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(generateExpirationDate())
						.signWith(key, SIGNATURE_ALGORITHM)
						.compact();
		return token;
	}
	public String refreshToken(String token) {
		String refreshedToken;
		Date a = new Date(System.currentTimeMillis());
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			Key key = Keys.hmacShaKeyFor(KEY.getBytes());
			claims.setIssuedAt(a);
			refreshedToken = Jwts.builder()
				.setClaims(claims)
				.signWith(key, SIGNATURE_ALGORITHM)
				.setExpiration(generateExpirationDate())
				.compact();
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}
	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		return (
				username != null &&
				username.equals(userDetails.getUsername()) &&
				!isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()) &&
				user.isEnabled() &&
				user.isAccountNonExpired() &&
				user.isAccountNonLocked()
		);
	}

	public String getToken( HttpServletRequest request ) {
		return request.getHeader(AUTH_HEADER);
	}
	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}
	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRATION_TIME*1000);
	}
	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
					.build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}


}