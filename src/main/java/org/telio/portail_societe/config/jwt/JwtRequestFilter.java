package org.telio.portail_societe.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.telio.portail_societe.metier.interfaces.IUserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Data
@NoArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    /**
     * Au niveau de l'entête de la requête (Header), le token est envoyé dans la
     * clé "Authorization". La valeur de cette clé est : Bearer
     * eyJhbGciOiJIUzUxMiJ9. (l'entête du token)
     * eyJzdWIiOiJjbGllbnQxIiwiZXhwIjoxNTU5MjU2NTg4LCJpYXQiOjE1NTkyMzg1ODh9. (le contenu du token)
     * sAFGCahWhLopAbPKyosOrep717jnHDQ9B_iU2o4QzcAiKEdAw62KXcXb3bfiRTTYwNib_0B-B7HUeVjaldZq9g (la signature du token)
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        System.out.println(requestTokenHeader);

        // supprimer le mot Bearer est ne récupérer que le token.
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        // Une fois on récupère le token, on procéde à sa validation.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            // Si le token est valide, on configure Spring Security en ajoutant
            // Authentication au niveau du contexte
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Préciser que l'utilisateur est bien authentifié.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}

