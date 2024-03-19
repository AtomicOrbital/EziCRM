package com.example.crm.filter;

import com.example.crm.utils.JwtHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

// Filter này dùng để lấy token và giải mã token
@Service
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy giá trị của header có giá trị là Authorization
        String authen = request.getHeader("Authorization");
        if(authen != null && authen.startsWith("Bearer ")){
            // cắt chuỗi từ biến authen để lấy ra được token
            String token = authen.substring(7);
            if(!token.isEmpty()){
                // Giải mã token
                try {
                    // Lấy danh sách role đã lưu trữ trong token
                    String data = jwtHelper.validationToken(token);
                    // parser chuoi danh sach role thanh list
                    Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>(){}.getType();
                    List<SimpleGrantedAuthority>  roles = gson.fromJson(data, listType);
                    // Tạo chứng thực theo chuẩn security
//                    List<GrantedAuthority> roles = new ArrayList<>();
//                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
//                    roles.add(authority);


                    UsernamePasswordAuthenticationToken tokenAuthen = new UsernamePasswordAuthenticationToken("", "", roles);

                    SecurityContext context = SecurityContextHolder.getContext();
                    context.setAuthentication(tokenAuthen);
                } catch(Exception e){
                    System.out.println("Loi giai ma token " + e.getLocalizedMessage());
                }

            }
        }
//
//        System.out.println("Kiem tra " + authen);



        filterChain.doFilter(request, response);
    }
}
