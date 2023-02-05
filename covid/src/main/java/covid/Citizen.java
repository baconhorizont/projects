package covid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Citizen {

    private Long id;
    private  String name;
    private  String zipCode;
    private  int age;
    private  String email;
    private  String socialSecurityNumber;
    private int numberOfVaccination;
    private LocalDateTime lastVaccination;

    public Citizen(String name, String zipCode, int age, String email, String socialSecurityNumber) {
        this.name = name;
        this.zipCode = zipCode;
        this.age = age;
        this.email = email;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Citizen(Long id, String name, String zipCode, int age, String email, String socialSecurityNumber,int numberOfVaccination, LocalDateTime lastVaccination) {
        this(name, zipCode, age, email, socialSecurityNumber);
        this.id = id;
        this.numberOfVaccination = numberOfVaccination;
        this.lastVaccination = lastVaccination;
    }

    public Citizen(Long id, String name, String zipCode, int age, String email, String socialSecurityNumber,int numberOfVaccination) {
        this(name, zipCode, age, email, socialSecurityNumber);
        this.id = id;
        this.numberOfVaccination = numberOfVaccination;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public int getNumberOfVaccination() {
        return numberOfVaccination;
    }

    public LocalDateTime getLastVaccination() {
        return lastVaccination;
    }

}
