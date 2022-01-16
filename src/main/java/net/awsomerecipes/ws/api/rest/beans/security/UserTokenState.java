package net.awsomerecipes.ws.api.rest.beans.security;

public class UserTokenState {
	private String jwtToken;
	private Long expires_in;
	private String role;

	public UserTokenState() {}
	public UserTokenState(String access_token, Long expires_in) {
		this.jwtToken = access_token;
		this.expires_in = expires_in;
	}
	public UserTokenState(String access_token, Long expires_in, String role) {
		this.jwtToken = access_token;
		this.expires_in = expires_in;
		this.role = role;
	}

	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}