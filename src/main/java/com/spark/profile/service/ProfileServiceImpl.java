package com.spark.profile.service;


import com.spark.profile.model.Profile;
import com.spark.profile.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    final String dir = System.getProperty("user.dir");
    @Value("${profilePictureDirectory}")
    private String profilePictureDirectory;
    @Value("${pathSeparator}")
    private String pathSeparator;

    public void saveOrUpdateExpense(Profile profile) {
        profileRepository.save(profile);
    }

    public void deleteProfilById(long id) {
        profileRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        profileRepository.deleteAll();
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }


    @Override
    public Profile findById(long id) {
        Optional<Profile> profile = profileRepository.findById(id);
        return profile.orElse(null);
    }

}
