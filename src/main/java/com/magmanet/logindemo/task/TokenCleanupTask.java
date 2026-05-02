package com.magmanet.logindemo.task;

import com.magmanet.logindemo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupTask {

    @Autowired
    private RefreshTokenRepository repo;

    // 每 1 小时执行一次 //delete token expiry
    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredTokens() {
        repo.deleteAllByExpiryDateBefore(System.currentTimeMillis());
        System.out.println("🧹 Cleaned expired refresh tokens");
    }
}
