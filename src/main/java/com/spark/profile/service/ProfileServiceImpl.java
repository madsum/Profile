package com.spark.profile.service;


import com.spark.profile.exception.FileStorageException;
import com.spark.profile.exception.ProfileNotFoundException;
import com.spark.profile.model.Profile;
import com.spark.profile.repository.ProfileRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Service
public class ProfileServiceImpl implements ProfileService {

    private static final Logger logger = LogManager.getLogger(ProfileServiceImpl.class);

    @Autowired
    private ProfileRepository profileRepository;

    private final String dir = System.getProperty("user.dir");
    @Value("${profilePictureDirectory}")
    private String profilePictureDirectory;
    @Value("${pathSeparator}")
    private String pathSeparator;

    public Profile saveOrUpdateProfile(Profile profile) {
       return profileRepository.save(profile);
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
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        Profile profile;
        if(optionalProfile.isPresent()){
            profile = optionalProfile.get();
        }else{
            throw new ProfileNotFoundException("Profile id: "+id+" is not found");
        }
        return profile;
    }

    public String storeFile(MultipartFile file, String path) {
        String imageDir =  dir+pathSeparator+profilePictureDirectory+pathSeparator;
        try {
            Files.createDirectories(Paths.get(imageDir));
        } catch (IOException e) {
            logger.fatal(imageDir+" Image directory does not exist");
            throw new FileStorageException("Image directory does not exist");
        }

        if(null != path){
            try {
                Files.deleteIfExists(Paths.get(imageDir+path));
            } catch (IOException e) {
                // ignore this exception
                logger.info("File delete exception. We can ignore it "+e.getMessage());
            }
        }
        if(file != null && file.getOriginalFilename() != null){
            String fileName =  generateTimestampFilename(file.getOriginalFilename());
            if(null == fileName || "" == fileName){
                throw new FileStorageException("file name not found");
            }
            String profilePicturePath = imageDir+fileName;
            try {
                // Copy file to the target location (Replacing existing file with the same name)
                Path targetLocation = Paths.get(profilePicturePath);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
            }
            return profilePicturePath;
        }else{
            throw new FileStorageException("file name not found");
        }
    }

    private String generateTimestampFilename(String fileName){
        String timestampFilename = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(null != fileName){
            String[] splitName =  fileName.split("\\.");
            if(splitName.length > 0){
                timestampFilename = splitName[0]+"-"+timestamp.toString()+"."+splitName[splitName.length-1];
            }
        }
        return timestampFilename;
    }

}
