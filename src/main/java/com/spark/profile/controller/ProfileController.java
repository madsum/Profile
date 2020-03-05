package com.spark.profile.controller;


import com.spark.profile.model.Profile;
import com.spark.profile.service.ProfileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ProfileController {

    private static final Logger logger = LogManager.getLogger(ProfileController.class);

    @Autowired
    ProfileService profileService;

    private String picturePath = null;

    /**
     * This will find profile by id.
     * @since 05-02-2020
     * @param id
     * @return Profile
     */

    @RequestMapping("/profile/{id}")
    public Profile findById(@PathVariable("id") long id) {
        return profileService.findById(id);
    }

    /**
     * This will find all profile.
     * @since 05-02-2020
     * @return List<Profile>
     */

    @RequestMapping("/all/profile")
    public  List<Profile> findAll() {
        return profileService.findAll();
    }

    /**
     * This will take a file and existing image path. File will save.
     * Existing image path will remove it.
     * @since 05-02-2020
     * @param file
     * @param path
     * @return picturePath
     */

    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public String saveOrUpdatePhoto(@RequestParam("file") MultipartFile file, String path) {
        logger.info("file name:" + file.getOriginalFilename());
        if(file != null){
            picturePath = profileService.storeFile(file, path);
        }
        return picturePath;
    }

    /**
     * This will take a profile. It will save if new profile.
     * It will update any existing profile
     * @since 05-02-2020
     * @param profile
     * @return ResponseEntity
     */

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<?> saveOrUpdateProfile(@RequestBody Profile profile) {
        profile.setPicture_path(picturePath);
        picturePath = null;
        profileService.saveOrUpdateProfile(profile);
        return new ResponseEntity<>("Profile submission successfully", HttpStatus.OK);
    }

    /**
     * This will take profile id. It will delete profile given id.
     * @since 05-02-2020
     * @param id
     */

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.DELETE)
    public void deleteProfileById(@PathVariable("id") long id) {
        profileService.deleteProfilById(id);
    }

    /**
     * This will delete all profile.
     * @since 05-02-2020
     */

    @RequestMapping(value = "/profile/deleteAll", method = RequestMethod.DELETE)
    public void deleteAllProfile() {
        profileService.deleteAll();
    }
}
