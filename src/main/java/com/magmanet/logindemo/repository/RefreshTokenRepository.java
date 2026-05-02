package com.magmanet.logindemo.repository;

import com.magmanet.logindemo.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    //delete token expiry
    void deleteAllByExpiryDateBefore(long time);
}
