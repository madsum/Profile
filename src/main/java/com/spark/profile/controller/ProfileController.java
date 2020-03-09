package com.spark.profile.controller;


import com.spark.profile.model.Profile;
import com.spark.profile.service.ProfileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class ProfileController {

    private static final Logger logger = LogManager.getLogger(ProfileController.class);

    @Autowired
    ProfileService profileService;

    @Value("${profilePictureDirectory}")
    private String profilePictureDirectory;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage() {
        return "Welcome to the Spark dating service. Please go to http://localhost:3000/ ";
    }

    /**
     * This will find profile by id.
     *
     * @param id
     * @return Profile
     * @since 05-02-2020
     */

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public Profile findById(@PathVariable("id") long id) {
        return profileService.findById(id);
    }

    /**
     * This will find all profile.
     *
     * @return List<Profile>
     * @since 05-02-2020
     */

    @RequestMapping(value = "/all/profile", method = RequestMethod.GET)
    public List<Profile> findAll() {
        return profileService.findAll();
    }

    /**
     * This will take a file and existing image path. File will save.
     * Existing image path will remove it.
     *
     * @param file
     * @param path
     * @return picturePath
     * @since 05-02-2020
     */

    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public String saveOrUpdatePhoto(@RequestParam("file") MultipartFile file, String path) {
        logger.info("file name:" + file.getOriginalFilename());
        if (file != null) {
            return profileService.storeFile(file, path);
        } else {
            return null;
        }
    }

    /**
     * This will take a profile. It will save if new profile.
     * It will update any existing profile
     *
     * @param profile
     * @return ResponseEntity
     * @since 05-02-2020
     */

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<?> saveOrUpdateProfile(@RequestBody Profile profile) {
        profile.setFileDownloadUri(profileService.getFileDownloadUri());
        profileService.saveOrUpdateProfile(profile);
        profileService.setFileDownloadUri("");
        return new ResponseEntity<>("Profile submission successfully", HttpStatus.OK);
    }

    /**
     * This will take profile id. It will delete profile given id.
     *
     * @param id
     * @since 05-02-2020
     */

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.DELETE)
    public void deleteProfileById(@PathVariable("id") long id) {
        profileService.deleteProfilById(id);
    }

    /**
     * This will delete all profile.
     *
     * @since 05-02-2020
     */

    @RequestMapping(value = "/profile/deleteAll", method = RequestMethod.DELETE)
    public void deleteAllProfile() {
        profileService.deleteAll();
    }

    @RequestMapping(value = "/image/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = profileService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
