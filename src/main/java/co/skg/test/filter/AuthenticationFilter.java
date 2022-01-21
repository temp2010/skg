package co.skg.test.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class AuthenticationFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		final MultivaluedMap<String, String> headers = requestContext.getHeaders();
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		if (authorization == null || authorization.isEmpty()) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Access denied").build());
			return;
		}

		String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		String usernamePassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
		StringTokenizer tokenizer = new StringTokenizer(usernamePassword, ":");
		String username = tokenizer.nextToken();
		String password = tokenizer.nextToken();

		if (!allowed(username, password)) {
			requestContext
					.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Access denied").build());
			return;
		}
	}

	private boolean allowed(String username, String password) {
		if (username.equals("skg") && password.equals("test"))
			return true;

		return false;
	}

}