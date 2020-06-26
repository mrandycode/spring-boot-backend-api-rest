package com.yo.minimal.rest.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 30)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 30)
    private String lastname;

    @NotNull
    @NotEmpty
    private String birthday;

    @Column(name = "place_born")
    @Size(min = 4, max = 100)
    private String placeBorn;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 1)
    private String nacionality;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 1)
    private String genre;

    @Column(name = "identification_id")
    @NotEmpty
    @NotNull
    private String identificationId;

    @Column(name = "create_date")
    private String createDate;

    @NotNull
    @Column(unique = true)
    @Email
    private String email;

    private String photo;

    @Size(min = 1, max = 255)
    private String address;

    @Size(min = 1, max = 255)
    private String addressTwo;

    @Size(min = 1, max = 255)
    private String addressThree;

    @Column(name = "local_phone")
    @Size(min = 1, max = 50)
    private String localPhone;

    @Column(name = "mobile_phone")
    @Size(min = 1, max = 50)
    private String mobilePhone;

    @Column(name = "work_phone")
    @Size(min = 1, max = 50)
    private String workPhone;

    @Column(name = "socialmedia_one")
    @Size(min = 1, max = 100)
    private String socialMediaOne;

    @Size(min = 1, max = 100)
    @Column(name = "socialmedia_two")
    private String socialMediTwo;

    @Column(name = "socialmedia_three")
    @Size(min = 1, max = 50)
    private String socialMediaThree;

    // Constructor sin argumentos.
    public Customer() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPlaceBorn() {
        return placeBorn;
    }

    public void setPlaceBorn(String placeBorn) {
        this.placeBorn = placeBorn;
    }

    public String getNacionality() {
        return nacionality;
    }

    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getAddressThree() {
        return addressThree;
    }

    public void setAddressThree(String addressThree) {
        this.addressThree = addressThree;
    }

    public String getLocalPhone() {
        return localPhone;
    }

    public void setLocalPhone(String localPhone) {
        this.localPhone = localPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getSocialMediaOne() {
        return socialMediaOne;
    }

    public void setSocialMediaOne(String socialMediaOne) {
        this.socialMediaOne = socialMediaOne;
    }

    public String getSocialMediTwo() {
        return socialMediTwo;
    }

    public void setSocialMediTwo(String socialMediTwo) {
        this.socialMediTwo = socialMediTwo;
    }

    public String getSocialMediaThree() {
        return socialMediaThree;
    }

    public void setSocialMediaThree(String socialMediaThree) {
        this.socialMediaThree = socialMediaThree;
    }

    private static final long serialVersionUID = 1L;
}