package com.magmanet.logindemo.service;

import com.magmanet.logindemo.entity.User;
import com.magmanet.logindemo.repository.UserRepository;
import com.magmanet.logindemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.magmanet.logindemo.repository.RefreshTokenRepository;
import com.magmanet.logindemo.entity.RefreshToken;

import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(String email, String password) {

        // 1. 检查用户是否存在
        if (userRepository.findByEmail(email).isPresent()) {
            return "User already exists";
        }

        // 2. 加密密码
        String encodedPassword = passwordEncoder.encode(password);

        // 3. 创建用户
        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole("ROLE_USER");

        // 4. 保存
        userRepository.save(user);

        return "Register success";
    }

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, String> login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        //generate refresh token
        String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
        String refreshToken = createRefreshToken(user);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    //generate refresh token
    @Autowired
    private RefreshTokenRepository refreshRepo;

    public String createRefreshToken(User user) {

        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUserId(user.getId());
        refreshToken.setExpiryDate(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

        refreshRepo.save(refreshToken);

        return token;
    }

    public Map<String, String> refresh(String refreshToken) {

        RefreshToken token = refreshRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiryDate() < System.currentTimeMillis()) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return Map.of("accessToken", newAccessToken);
    }


}
