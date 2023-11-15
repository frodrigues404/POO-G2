package br.edu.atitus.poo.atitusound.components;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; // Adicionando a anotação @Component

import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.atitus.poo.atitusound.services.UserService;
import br.edu.atitus.poo.atitusound.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Adicionando a anotação @Component para que o Spring a reconheça
public class AuthTokenFilter extends OncePerRequestFilter {
	
	private final UserService userService;

	@Autowired // Adicionando a anotação @Autowired para a injeção de dependência
	public AuthTokenFilter(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = JwtUtil.getJwtFromRequest(request);
		if (jwt != null && JwtUtil.validateJwtToken(jwt)) {
			String username = JwtUtil.getUserNameFromJwtToken(jwt);
			var user = userService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, null);

			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);
	}
}
