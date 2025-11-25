package com.example.assignment_three_zelora.model.entitys;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_default")
    private boolean isDefault;

    public Address(Integer addressId, Customer customer, String fullName, String addressLine1,
                   String addressLine2, String city, String postalCode, String country,
                   String phoneNumber, boolean isDefault) {
        this.addressId = addressId;
        this.customer = customer;
        this.fullName = fullName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.isDefault = isDefault;
    }

    public Address() {
    }

    public Integer getAddressId() {
        return addressId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "Address(addressId=" + this.addressId +
                ", fullName=" + this.fullName +
                ", city=" + this.city + ")";
    }
}
