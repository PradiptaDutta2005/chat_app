package com.pradipta.chat_app.service;

import com.pradipta.chat_app.entity.User;
import com.pradipta.chat_app.repository.UserRepository;
import com.pradipta.chat_app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder encoder;
    public String register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));  // 🔥 HASH
        repo.save(user);
        return "User registered";
    }

    public String login(User user) {

        User dbUser = repo.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return JwtUtil.generateToken(user.getUsername());
    }
}