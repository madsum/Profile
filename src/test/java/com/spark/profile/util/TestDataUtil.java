package com.spark.profile.util;

import com.spark.profile.model.Profile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestDataUtil {

    public static Profile createProfile(){
        Profile profile = new Profile();
        profile.setDisplay_name("display name");
        profile.setReal_name("real name");
        profile.setGender("male");
        return profile;
    }

    public static List<Profile> createProfileList(int numOfElements){
        String realName = "real name";
        return IntStream.range(0, numOfElements)
                .mapToObj(i -> {Profile p = new Profile();
                    p.setReal_name(realName+i);
                    return p;})
                .collect(Collectors.toList());
    }

}
