package com.cmc.dcb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.dcb.entity.TokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, String>{

}
