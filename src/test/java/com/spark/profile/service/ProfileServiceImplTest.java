package com.spark.profile.service;

import com.spark.profile.util.TestDataUtil;
import com.spark.profile.exception.FileStorageException;
import com.spark.profile.exception.ProfileNotFoundException;
import com.spark.profile.model.Profile;
import com.spark.profile.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProfileServiceImplTest {
    @InjectMocks
    ProfileServiceImpl profileServiceImpl;

    @Mock
    ProfileRepository profileRepository;

    @Mock
    ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindByIdTest() {
        // arrange
        Profile expectedProfile = TestDataUtil.createProfile();
        // act
        when(profileRepository.findById(1l)).thenReturn(java.util.Optional.of(expectedProfile));
        Profile actualProfile =  profileServiceImpl.findById(1l);

        // assert
        assertNotNull(actualProfile);
        assertSame(actualProfile, expectedProfile);
    }

    @Test
    void testGetAllTest() {
        // arrange
        List<Profile> expectProfiles = TestDataUtil.createProfileList(5);

        // act
        when(profileRepository.findAll()).thenReturn(expectProfiles);
        List<Profile> actualProfiles = profileServiceImpl.findAll();

        // assert
        assertNotNull(actualProfiles);
        assertEquals(actualProfiles.size(), expectProfiles.size());
        assertIterableEquals(actualProfiles, expectProfiles);
    }

    @Test
    void testThrowFileStorageExceptionWhenNull(){
        // act
        when(profileService.storeFile(null, null)).thenReturn(null);
        //assert
        assertThrows(FileStorageException.class,
                () -> {profileServiceImpl.storeFile(null,null);}
        );
    }

    @Test
    void testThrowProfileNotFoundException(){
        // act
        when(profileService.findById(100l)).thenReturn(null);
        // assert
        assertThrows(ProfileNotFoundException.class,
                () -> {profileServiceImpl.findById(100l);}
        );
    }
}