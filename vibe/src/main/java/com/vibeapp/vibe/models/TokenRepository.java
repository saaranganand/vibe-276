package com.vibeapp.vibe.models;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);
    Token findByEmail(String email);
    @Modifying
    @Transactional
    @Query("delete from Token t where t.expirationDate <= ?1")
    void deleteAllExpiredSince(Date current);
}
