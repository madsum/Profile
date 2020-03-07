package com.spark.profile.controller;

import com.spark.profile.model.Profile;
import com.spark.profile.service.ProfileService;
import com.spark.profile.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindByIdTest() {
        // arrange
        Profile expectedProfile = TestDataUtil.createProfile();

        // act
        when(profileService.findById(1l)).thenReturn(expectedProfile);
        Profile actualProfile = profileController.findById(1l);

        // assert
        assertNotNull(actualProfile);
        assertSame(actualProfile, expectedProfile);
    }

    @Test
    void testGetAllTest() {
        // arrange
        List<Profile> expectProfiles = TestDataUtil.createProfileList(5);

        // act
        when(profileService.findAll()).thenReturn(expectProfiles);
        List<Profile> actualProfiles = profileController.findAll();

        // assert
        assertNotNull(actualProfiles);
        assertEquals(actualProfiles.size(), expectProfiles.size());
        assertIterableEquals(actualProfiles, expectProfiles);
    }

    @Test
    void testAddOrUpdateProfile() {
        // arrange
        Profile profile = TestDataUtil.createProfile();
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // act
        when(profileService.saveOrUpdateProfile(profile)).thenReturn(profile);
        ResponseEntity<?> responseEntity = profileController.saveOrUpdateProfile(profile);

        // assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), "Profile submission successfully");
    }

    @Test
    void testSaveOrUpdatePhoto() {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request));
        MockMultipartFile file = TestDataUtil.getMockMultipartFile();
        String exitingPhotoPath = "exitingPhotoPath";
        String expctedNewPhotoPath = "newPhotoPath";

        // act
        when(profileService.storeFile(file, exitingPhotoPath))
                .thenReturn(expctedNewPhotoPath);
        String actualNewPhotoPath = profileController.saveOrUpdatePhoto(file, exitingPhotoPath);

        // Assert
        assertEquals(actualNewPhotoPath, expctedNewPhotoPath);
    }
}