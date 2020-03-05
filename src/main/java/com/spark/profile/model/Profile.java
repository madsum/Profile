package com.spark.profile.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    String display_name;

    @NotNull
    String real_name;

    String picture_path;

    @NotNull
    Date birth_date;

    @NotNull
    String gender;

    String ethnicity;

    String religion;

    double height;

    String figure;

    @NotNull
    String marital_status;

    String occupation;

    String about_me;

    @NotNull
    String city_location;

    @CreationTimestamp
    private java.util.Date created_at;

    @UpdateTimestamp
    private java.util.Date updated_at;

    public Profile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getCity_location() {
        return city_location;
    }

    public void setCity_location(String city_location) {
        this.city_location = city_location;
    }

    public java.util.Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(java.util.Date created_at) {
        this.created_at = created_at;
    }

    public java.util.Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(java.util.Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", displayName='" + display_name + '\'' +
                ", realName='" + real_name + '\'' +
                ", picturePath='" + picture_path + '\'' +
                ", birthDate=" + birth_date +
                ", gender='" + gender + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", religion='" + religion + '\'' +
                ", height=" + height +
                ", figure='" + figure + '\'' +
                ", maritalStatus='" + marital_status + '\'' +
                ", occupation='" + occupation + '\'' +
                ", aboutMe='" + about_me + '\'' +
                ", cityLocation='" + city_location + '\'' +
                ", createdAt=" + created_at +
                ", updatedAt=" + updated_at +
                '}';
    }
}
