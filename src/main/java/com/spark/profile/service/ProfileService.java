package com.spark.profile.service;

import com.spark.profile.model.Profile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    Profile saveOrUpdateProfile(Profile profile);

    void deleteProfilById(long id);

    void deleteAll();

    List<Profile> findAll();

    Profile findById(long id);

    String storeFile(MultipartFile file, String path);

    String getFileDownloadUri();

    void setFileDownloadUri(String fileDownloadUri);

    Resource loadFileAsResource(String fileName);
}
