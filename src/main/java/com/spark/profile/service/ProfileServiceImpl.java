package com.spark.profile.service;


import com.spark.profile.exception.FileNotFoundException;
import com.spark.profile.exception.FileStorageException;
import com.spark.profile.exception.ProfileNotFoundException;
import com.spark.profile.model.Profile;
import com.spark.profile.repository.ProfileRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    private static final Logger logger = LogManager.getLogger(ProfileServiceImpl.class);

    @Autowired
    private ProfileRepository profileRepository;

    private String fileDownloadUri = null;

    private String pathSeparator;

    String profilePictureDirectory;

    private final Path fileStorageLocation;

    public ProfileServiceImpl(@Value("${profilePictureDirectory}")
                                      String profilePictureDirectory,
                              @Value("${pathSeparator}") String pathSeparator) {
        fileStorageLocation = Paths.get(System.getProperty("user.dir") + pathSeparator
                + profilePictureDirectory)
                .toAbsolutePath().normalize();
        this.pathSeparator = pathSeparator;
        this.profilePictureDirectory = profilePictureDirectory;
    }

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
        return profileRepository.findAll().stream()
                .map(ProfileServiceImpl::removeCityLatLon)
                .collect(Collectors.toList());
    }

    private static Profile removeCityLatLon(Profile profile) {
        if (Objects.isNull(profile.getCity_location())) {
            return profile;
        }
        String[] splitStr = profile.getCity_location().split("#");
        if (splitStr.length > 0) {
            profile.setCity_location(splitStr[0]);
            return profile;
        } else {
            return profile;
        }
    }

    @Override
    public Profile findById(long id) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        Profile profile;
        if (optionalProfile.isPresent()) {
            profile = optionalProfile.get();
        } else {
            throw new ProfileNotFoundException("Profile id: " + id + " is not found");
        }
        return profile;
    }

    public String storeFile(MultipartFile file, String path) {
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            logger.fatal(fileStorageLocation.toString() + " Image directory does not exist");
            throw new FileStorageException("Image directory does not exist");
        }

        if (!Objects.isNull(path) && !Objects.equals(path, "")) {
            try {
                String[] split = path.split(pathSeparator);
                Files.deleteIfExists(Paths.get(fileStorageLocation + pathSeparator + split[split.length - 1]));
            } catch (IOException e) {
                // ignore this exception
                logger.info("File delete exception. We can ignore it " + e.getMessage());
            }
        }
        if (!Objects.isNull(file) && !Objects.isNull(file.getOriginalFilename())) {
            String fileName = generateTimestampFilename(file.getOriginalFilename());
            String profilePicturePath = fileStorageLocation + pathSeparator + fileName;
            try {
                // Copy file to the target location (Replacing existing file with the same name)
                Path targetLocation = Paths.get(profilePicturePath);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
            }
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(pathSeparator + profilePictureDirectory + pathSeparator + fileName)
                    .toUriString();
            return profilePicturePath;
        } else {
            throw new FileStorageException("file name not found");
        }
    }

    private String generateTimestampFilename(String fileName) {
        String timestampFilename = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String strTimestamp = timestamp.toString();
        strTimestamp = strTimestamp.replaceAll("\\s", "");
        if (null != fileName) {
            String[] splitName = fileName.split("\\.");
            if (splitName.length > 0) {
                timestampFilename = splitName[0] + "-" + strTimestamp + "." + splitName[splitName.length - 1];
            }
        }
        return timestampFilename;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(fileStorageLocation + pathSeparator + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

}
