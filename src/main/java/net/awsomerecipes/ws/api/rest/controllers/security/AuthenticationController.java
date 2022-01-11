package net.awsomerecipes.ws.api.rest.controllers.security;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.awsomerecipes.ws.api.rest.beans.User;
import net.awsomerecipes.ws.api.rest.beans.security.JwtAuthenticationRequest;
import net.awsomerecipes.ws.api.rest.beans.security.UserTokenState;
import net.awsomerecipes.ws.api.rest.config.security.TokenHelper;
import net.awsomerecipes.ws.api.rest.facades.UserFacade;

@RestController
@RequestMapping(path="/auth")
public class AuthenticationController {

	@Autowired
	TokenHelper tokenHelper;

	@Lazy
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserFacade userFacade;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody User user) {
		if (userFacade.findByEmail(user.getEmail()) != null
				|| userFacade.findByUsername(user.getUsername()) != null) {
			return ResponseEntity.ok("Already registered");
		}
		userFacade.newUser(user);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		// Inject into security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User) authentication.getPrincipal();
		String jws = tokenHelper.generateToken(user.getUsername());
		Long expiresIn = tokenHelper.getExpirationTime();

		return ResponseEntity.ok(new UserTokenState(jws, expiresIn, user.getRole().getName()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response,
			Principal principal) {

		String authToken = tokenHelper.getToken(request);

		if (authToken != null && principal != null) {
			String refreshedToken = tokenHelper.refreshToken(authToken);
			Long expiresIn = tokenHelper.getExpirationTime();

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));

		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.accepted().body(userTokenState);

		}
	}

	@PostMapping("/change-password")
	@PreAuthorize("hasAnyAuthority('user','chef')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userFacade.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}