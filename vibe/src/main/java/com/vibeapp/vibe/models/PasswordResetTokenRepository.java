package com.vibeapp.vibe.models;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByEmail(String email);
    @Modifying
    @Transactional
    @Query("delete from PasswordResetToken t where t.expirationDate <= ?1")
    void deleteAllExpiredSince(Date current);
}
