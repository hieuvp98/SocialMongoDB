package com.teme.spring.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "User")
public class AppUser {
    @Id
    private ObjectId id;
    private String idUser;
    private String email;
    private String name;
    private String profileImage;
    private String role;

    public AppUser() {
    }

    public AppUser(ObjectId id, String idUser, String email, String name, String profileImage, String role) {
        this.id = id;
        this.idUser = idUser;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.role = role;
    }

    public String getId() {
        return id.toHexString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
