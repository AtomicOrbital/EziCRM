package com.example.crm.provider;

import com.example.crm.entity.UserEntity;
import com.example.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomAuthenProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity user = userRepository.findByEmail(username);
        if(user != null){
            // User tồn tại trong csdl thì kiểm tra tiếp password
            if(passwordEncoder.matches(password, user.getPassword())){
                // Tạo chứng thực theo chuẩn security
                List<GrantedAuthority> roles = new ArrayList<>();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
                roles.add(authority);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword(), roles);

                return token;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Khai báo kiểu chứng thực hỗ trợ
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
