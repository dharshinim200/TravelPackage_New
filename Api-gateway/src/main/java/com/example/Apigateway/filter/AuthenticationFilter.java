package com.example.Apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.example.Apigateway.util.JwtUtil;
import com.google.common.net.HttpHeaders;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtUtil util;

	public static class Config {
	}

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					return handleUnauthorized(exchange.getResponse(), "Missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

				try {
					String role = util.extractRolesFromToken(authHeader);
					String requestedPath = exchange.getRequest().getPath().toString();
					String method = exchange.getRequest().getMethod().name();

					if (!isAuthorized(role, requestedPath, method)) {
						return handleUnauthorized(exchange.getResponse(), "Unauthorized access");
					}

				} catch (Exception e) {
					return handleUnauthorized(exchange.getResponse(), "Invalid token");
				}
			}
			return chain.filter(exchange);
		};
	}

	private boolean isAuthorized(String role, String path, String method) {
		if ("ADMIN".equalsIgnoreCase(role)||"admin".equalsIgnoreCase(role)) {
			return path.startsWith("/user")
					|| (path.startsWith("/payment") && !method.equalsIgnoreCase("PUT")
							&& !method.equalsIgnoreCase("DELETE") && !method.equalsIgnoreCase("POST"))
					|| path.startsWith("/package") || path.startsWith("/review") || (path.startsWith("/booking")
							&& !method.equalsIgnoreCase("POST") && !method.equalsIgnoreCase("POST"));
		} else if ("USER".equalsIgnoreCase(role)||"user".equalsIgnoreCase(role)) {
			return (path.startsWith("/user") && !path.startsWith("/user/fetchAll"))

					|| ((path.startsWith("/payment") && !path.startsWith("/payment/fetchAll"))
							&& !method.equalsIgnoreCase("DELETE"))

					|| (path.startsWith("/package") && !method.equalsIgnoreCase("DELETE")
							&& !method.equalsIgnoreCase("PUT") && !method.equalsIgnoreCase("POST"))
					|| path.startsWith("/review")
					|| (path.startsWith("/booking") && !path.startsWith("/booking/fetchAll"));

		} else if ("TRAVEL AGENTS".equalsIgnoreCase(role)) {
			return path.startsWith("/user")
					|| path.startsWith("/payment") && !method.equalsIgnoreCase("PUT")
							&& !method.equalsIgnoreCase("DELETE") && !method.equalsIgnoreCase("POST")
					|| path.startsWith("/package") || path.startsWith("/review") || (path.startsWith("/booking")
							&& !method.equalsIgnoreCase("POST") && !method.equalsIgnoreCase("DELETE"));
		}
		return false;
	}

	private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
		response.setStatusCode(HttpStatus.FORBIDDEN);
		return response.setComplete();
	}
}
