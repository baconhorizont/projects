package covid;

import java.util.Scanner;

public class VaccinationController {

    private VaccinationService vaccinationService = new VaccinationService();
    private Scanner scanner = new Scanner(System.in);
    private static final int MENU_EXIT = 7;

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
            case 2 -> System.out.println("Tömeges regisztráció");
            case 3 -> System.out.println("Generálás");
            case 4 -> System.out.println("Oltás");
            case 5 -> System.out.println("Oltás meghiúsulás");
            case 6 -> System.out.println("Riport");
            case 7 -> System.out.println("Program vége.");
            default -> System.out.println("Nem létező menüpont!");
        }
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
           // recallRegistration(wdie);
        }
            System.out.println("Regisztráció befejezve.");
    }

    private String scanSscNumber() {
        System.out.println("Tajszám:");
        String sscNumber = scanner.nextLine();
        vaccinationService.validateSscNumber(sscNumber);
        return sscNumber;
    }

    //    private void recallRegistration(WrongDataInputException wdie) {
//        switch (wdie.getMessage()) {
//            case "A név megadása kötelező." -> scanName();
//            case "Helytelen vagy üresen hagyott irányítószám." -> scanZipCode();
//            case "Helytelen életkor." -> scanAge();
//            case "Helytelen email." -> scanEmail();
//            default -> scanForRegistration();
//        }
//        scanForRegistration();
//    }
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
