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
    private List<Vaccination> vaccinations;

    public Citizen(String name, String zipCode, int age, String email, String socialSecurityNumber) {
        this.name = name;
        this.zipCode = zipCode;
        this.age = age;
        this.email = email;
        this.socialSecurityNumber = socialSecurityNumber;
        this.vaccinations = new ArrayList<>();
    }


    public Citizen(Long id, String name, String zipCode, int age, String email, String socialSecurityNumber) {
        this(name, zipCode, age, email, socialSecurityNumber);
        this.id = id;
        this.vaccinations = new ArrayList<>();
    }

    public static Citizen createCitizen(String name, String zipCode, int age, String email, String socialSecurityNumber){
        return new Citizen(name,zipCode,age,email,socialSecurityNumber);
    }

    public void addVaccination(Vaccination vaccination){
        if(vaccination.getStatus().equals(VaccinationStatus.OK)){
            this.numberOfVaccination++;
            this.lastVaccination = vaccination.getVaccinationTime();
        }
        vaccinations.add(vaccination);
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

    public List<Vaccination> getVaccinations() {
        return List.copyOf(vaccinations);
    }

}
