package com.spark.profile.service;

import com.spark.profile.model.Profile;
import com.spark.profile.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProfileServiceImplTest {

    @InjectMocks
    ProfileServiceImpl profileServiceImpl;

    @Mock
    ProfileRepository profileRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void saveOrUpdateExpense() {
    }

    @Test
    void deleteProfileByIdTest() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void findByIdTest() {
        // expected
        Profile expectedProfile = new Profile();
        expectedProfile.setDisplayName("display name");
        expectedProfile.setRealName("real name");
        expectedProfile.setGender("male");

        // act
        when(profileRepository.findById(1l)).thenReturn(java.util.Optional.of(expectedProfile));
        Profile actualProfile =  profileServiceImpl.findById(1l);

        // assert
        assertNotNull(actualProfile);
        assertEquals(actualProfile.getDisplayName(), expectedProfile.getDisplayName());
    }

    @Test
    void getAllTest() {
        // expected
        List<Profile> expectProfiles = createProfileList(5);

        // act
        when(profileRepository.findAll()).thenReturn(expectProfiles);
        List<Profile> actualProfiles = profileServiceImpl.findAll();

        // assert
        assertNotNull(actualProfiles);
        assertEquals(actualProfiles.size(), expectProfiles.size());
        assertIterableEquals(actualProfiles, expectProfiles);
    }

    List<Profile> createProfileList(int numOfElements){
        String realName = "real name";
        return IntStream.range(0, numOfElements)
                .mapToObj(i -> {Profile p = new Profile();
                    p.setRealName(realName+i);
                    return p;})
                .collect(Collectors.toList());
    }

}