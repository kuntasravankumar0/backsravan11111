package com.project1.www.UserRepository;

import java.util.Optional;
import com.project1.www.UserModel.GoogleInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoogleInfoRepository extends JpaRepository<GoogleInfo, Long> {

    Optional<GoogleInfo> findByGoogleId(String googleId);
    
    Optional<GoogleInfo> findByEmail(String email);
    
    boolean existsByGoogleId(String googleId);
    
    boolean existsByEmail(String email);
    
    Optional<GoogleInfo> findByGoogleIdAndEmail(String googleId, String email);
}