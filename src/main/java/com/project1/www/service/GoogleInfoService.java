package com.project1.www.service;

import com.project1.www.UserModel.GoogleInfo;
import com.project1.www.UserRepository.GoogleInfoRepository;
import com.project1.www.dto.GoogleInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoogleInfoService {

    @Autowired
    private GoogleInfoRepository googleInfoRepository;

    public GoogleInfo saveOrUpdateGoogleInfo(GoogleInfoDTO googleInfoDTO) {
        // Check if Google ID already exists
        Optional<GoogleInfo> existingInfo = googleInfoRepository.findByGoogleId(googleInfoDTO.getGoogleId());
        
        GoogleInfo googleInfo;
        if (existingInfo.isPresent()) {
            // Update existing record
            googleInfo = existingInfo.get();
            googleInfo.setName(googleInfoDTO.getName());
            googleInfo.setEmail(googleInfoDTO.getEmail());
            googleInfo.setEmailVerified(googleInfoDTO.getEmailVerified());
            googleInfo.setPicture(googleInfoDTO.getPicture());
            googleInfo.setLocale(googleInfoDTO.getLocale());
            googleInfo.setAuthProvider(googleInfoDTO.getAuthProvider());
        } else {
            // Create new record
            googleInfo = new GoogleInfo(
                googleInfoDTO.getGoogleId(),
                googleInfoDTO.getName(),
                googleInfoDTO.getEmail(),
                googleInfoDTO.getEmailVerified(),
                googleInfoDTO.getPicture(),
                googleInfoDTO.getLocale(),
                googleInfoDTO.getAuthProvider()
            );
        }

        return googleInfoRepository.save(googleInfo);
    }

    public Optional<GoogleInfo> findByGoogleId(String googleId) {
        return googleInfoRepository.findByGoogleId(googleId);
    }

    public Optional<GoogleInfo> findByEmail(String email) {
        return googleInfoRepository.findByEmail(email);
    }

    public List<GoogleInfo> findAll() {
        return googleInfoRepository.findAll();
    }

    public boolean existsByGoogleId(String googleId) {
        return googleInfoRepository.existsByGoogleId(googleId);
    }

    public boolean existsByEmail(String email) {
        return googleInfoRepository.existsByEmail(email);
    }

    public void deleteByGoogleId(String googleId) {
        Optional<GoogleInfo> googleInfo = googleInfoRepository.findByGoogleId(googleId);
        googleInfo.ifPresent(googleInfoRepository::delete);
    }

    public long count() {
        return googleInfoRepository.count();
    }
}