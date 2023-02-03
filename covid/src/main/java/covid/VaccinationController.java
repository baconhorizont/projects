package covid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class VaccinationController {

    private VaccinationService vaccinationService = new VaccinationService();
    private Scanner scanner = new Scanner(System.in);
    private static final int MENU_EXIT = 7;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        VaccinationController vaccinationMain = new VaccinationController();
        vaccinationMain.run();
    }


    private void run() {
        int menuNumber = 0;
        while (menuNumber != MENU_EXIT) {
            printMenu();
            System.out.println("Menüpont száma: ");
            try {
                menuNumber = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Egész számot adjon meg!");
            }
            controlMenu(menuNumber);
        }
    }

    private void controlMenu(int menuNumber) {
        switch (menuNumber) {
            case 1 -> scanForRegistration();
            case 2 -> insertCitizensFromFile();
            case 3 -> scanForGeneration();
            case 4 -> vaccination();
            case 5 -> rejectVaccination();
            case 6 -> System.out.println("Riport");
            case 7 -> System.out.println("Program vége.");
            default -> System.out.println("Nem létező menüpont!");
        }
    }

    private void rejectVaccination(){
        try {
        String sscNumber = scanSscNumber();
        vaccinationService.validateVaccination(sscNumber);
        System.out.println("Oltás időpontja: yyyy-MM-dd HH:mm");
        String time = scanner.nextLine();
        LocalDateTime vaccinationTime = LocalDateTime.parse(time, FORMATTER);
        System.out.println("Meghíúsulás oka: ");
        String cause = scanner.nextLine();
        vaccinationService.rejectVaccination(sscNumber,vaccinationTime,cause);
        }catch (WrongDataInputException wdie){
            System.out.println(wdie.getMessage());
            System.out.println("Oltás meghíúsulásának mentése megszakadt, kezdje előről.");
        }
        System.out.println("Mentés befejezve.");
    }

    private void vaccination(){
        try {
        String sscNumber = scanSscNumber();
        vaccinationService.validateVaccination(sscNumber);
        Citizen citizen = vaccinationService.getCitizenBySscNumber(sscNumber);
        if(citizen.getNumberOfVaccination() == 0){
        System.out.println("Oltás időpontja: yyyy-MM-dd HH:mm");
        String time = scanner.nextLine();
        LocalDateTime vaccinationTime = LocalDateTime.parse(time, FORMATTER);
        System.out.println("Elérhető oltóanyag típusok: "  + Arrays.toString(VaccinationType.values()));
        System.out.println("Beadott óltóanyag típusa:");
        VaccinationType type = VaccinationType.valueOf(scanner.nextLine().toUpperCase());
        vaccinationService.vaccination(sscNumber,vaccinationTime,type);
        } else {
            Vaccination vaccination = vaccinationService.getVaccinationByCitizenId(citizen.getId());
            System.out.println("Már rendelkezik egy oltással.");
            System.out.println("Előző oltás időpontja: " + citizen.getLastVaccination());
            System.out.println("Oltóanyag: " + vaccination.getType());
            System.out.println("Második oltás időpontja: yyyy-MM-dd HH:mm");
            String time = scanner.nextLine();
            LocalDateTime vaccinationTime = LocalDateTime.parse(time, FORMATTER);
            vaccinationService.vaccination(sscNumber,vaccinationTime,vaccination.getType());
        }
        }catch (WrongDataInputException wdie){
            System.out.println(wdie.getMessage());
            System.out.println("Oltás megszakadt, kezdje előről.");
        }
        System.out.println("Oltás befejezve.");
    }


    private void scanForGeneration(){
        System.out.println("Generálás");
        try {
            String zipCode = scanZipCode();
            System.out.println("File neve:");
            String fileName = scanner.nextLine();
            vaccinationService.generateFile(zipCode,fileName);
        }catch (WrongDataInputException wdie){
            System.out.println(wdie.getMessage());
            System.out.println("Generálás megszakadt, kezdje előről.");
        }
        System.out.println("Generálás befejezve.");
    }

    private void scanForRegistration() {
        System.out.println("Regisztráció");
        try {
           String name = scanName();
           String zipCode = scanZipCode();
           int age = scanAge();
           String email = scanEmail();
           String sscNumber = scanSscNumber();
           vaccinationService.insertCitizen(name,zipCode,age,email,sscNumber);
        }catch (WrongDataInputException wdie){
            System.out.println(wdie.getMessage());
            System.out.println("Regisztráció megszakadt, kezdje előről.");
        }
            System.out.println("Regisztráció befejezve.");
    }

    private void insertCitizensFromFile(){
        System.out.println("File elérési útja:");
        String path = scanner.nextLine();
        vaccinationService.insertCitizensFromFile(path);
        System.out.println("A feltöltés siekres.");
    }
    private String scanSscNumber() {
        System.out.println("Tajszám:");
        String sscNumber = scanner.nextLine();
        vaccinationService.validateSscNumber(sscNumber);
        return sscNumber;
    }

    private int scanAge() {
        System.out.println("Életkor:");
        int age = Integer.parseInt(scanner.nextLine());
        vaccinationService.validateAge(age);
        return age;
    }

    private String scanZipCode() {
        System.out.println("Irányítószám:");
        String zipCode = scanner.nextLine();
        String cityName = vaccinationService.validateZipCodeAndGetCity(zipCode);
        System.out.println("Város: ".concat(cityName));
        return zipCode;
    }
    private String scanName() {
        System.out.println("Név:");
        String name = scanner.nextLine();
        vaccinationService.validateName(name);
        return name;
    }

    private String scanEmail() {
        System.out.println("Email:");
        String email = scanner.nextLine();
        vaccinationService.validateEmail(email);
        System.out.println("Email ellenőrzés:");
        String emailCheck = scanner.nextLine();
        vaccinationService.emailChecker(email,emailCheck);
        return email;
    }

    private void printMenu () {
        System.out.println();
        System.out.println(
                        """
                        1. Regisztráció
                        2. Tömeges regisztráció
                        3. Generálás
                        4. Oltás
                        5. Oltás meghiúsulás
                        6. Riport
                        7. Kilépés
                        """
        );
    }

}
