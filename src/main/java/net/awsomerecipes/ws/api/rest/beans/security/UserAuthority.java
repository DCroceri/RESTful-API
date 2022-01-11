package net.awsomerecipes.ws.api.rest.beans.security;

public enum UserAuthority {

	ROLE_ADMIN("admin"),
	ROLE_USER("user"),
	ROLE_CHEF("chef");

	private String roleName;
	UserAuthority (String roleName) {
		this.roleName = roleName;
	}
	public String getName() {
		return this.roleName;
	}
}
