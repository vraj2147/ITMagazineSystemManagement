package com.vraj.magazinesystem.util;



import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;
    private User user;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        user = new User();
        user.setId(1L);
        user.setRole(Role.EDITOR);
    }

    @Test
    void createToken_and_verifyContents() {
        String token = jwtTokenProvider.createToken(user);
        String decodedPayload = new String(Base64.getDecoder().decode(token));
        Assertions.assertEquals("1|EDITOR", decodedPayload);
    }

    @Test
    void getUserIdFromToken_correctId() {
        String token = jwtTokenProvider.createToken(user);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        Assertions.assertEquals(user.getId(), userId);
    }

    @Test
    void getRoleFromToken_correctRole() {
        String token = jwtTokenProvider.createToken(user);
        Role role = jwtTokenProvider.getRoleFromToken(token);
        Assertions.assertEquals(user.getRole(), role);
    }
}
