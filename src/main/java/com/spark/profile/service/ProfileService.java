package com.spark.profile.service;

import com.spark.profile.model.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    void saveOrUpdateExpense(Profile profile);

    void deleteProfilById(long id);

    void deleteAll();

    List<Profile> findAll();

    Profile findById(long id);

}
