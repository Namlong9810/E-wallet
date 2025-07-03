package e_wallet.shared_module.config;

import e_wallet.shared_module.adapter.UserDetailsServiceAdapter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtService;
    private final UserDetailsServiceAdapter userDetailsServiceAdapter;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authentication");
        final String token;
        final String username;

        //Pass token filter
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        };

        //Lấy acc token
        token = authHeader.substring(7);
        //Lấy Username người dùng
        username = jwtService.parseUserName(token);


        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = userDetailsServiceAdapter.loadUserByUsername(username); // Lay thong tin user
            if(jwtService.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, null); //Tạo token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //Add thông tin detail ở request vao token
                SecurityContextHolder.getContext().setAuthentication(authToken); //Lưu thông tin token vào SecurityContextHolder
            }
        }

        filterChain.doFilter(request, response);
    }
}
