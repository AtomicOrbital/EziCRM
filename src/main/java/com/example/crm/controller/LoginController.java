package com.example.crm.controller;

import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.response.SignInResponse;
import com.example.crm.utils.JwtHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password){
        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authen);

        List<GrantedAuthority> listRoles = (List<GrantedAuthority>) authentication.getAuthorities();
        String dataToken = gson.toJson(listRoles);
        String token = jwtHelper.generateToken(dataToken);

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String role = roles.isEmpty() ? null : roles.get(0);

        SignInResponse signInResponse = new SignInResponse(token, role);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(200);
        baseResponse.setMessage("SUCCESS");
        baseResponse.setData(signInResponse);
        return new ResponseEntity<>(baseResponse , HttpStatus.OK);

    }
}
