package net.awsomerecipes.ws.api.rest.beans.security;

public class UserTokenState {
	private String access_token;
	private Long expires_in;
	private String roleName;

	public UserTokenState() {}
	public UserTokenState(String access_token, Long expires_in) {
		this.access_token = access_token;
		this.expires_in = expires_in;
	}
	public UserTokenState(String access_token, Long expires_in, String roleName) {
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.roleName = roleName;
	}

	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}