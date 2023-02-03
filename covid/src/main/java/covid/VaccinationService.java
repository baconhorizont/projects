package covid;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VaccinationService {

    private static final String DELIMITER = ";";
    private static final String GENERATED_DOCUMENT_HEAD = "Időpont;Név;Irányítószám;Életkor;E-mail cím;TAJ szám";
    private MariaDbDataSource dataSource = new MariaDbDataSource();
    private CitizensRepository citizensRepository;
    private VaccinationsRepository vaccinationsRepository;
    private CitiesRepository citiesRepository;

    public VaccinationService() {
        initDbConnection();
        this.citizensRepository = new CitizensRepository(dataSource);
        this.vaccinationsRepository = new VaccinationsRepository(dataSource);
        this.citiesRepository = new CitiesRepository(dataSource);
    }

    private void initDbConnection() {
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/covid?useUnicode=true");
            dataSource.setUser("covid");
            dataSource.setPassword("covid");
            Flyway flyway = Flyway.configure().dataSource(dataSource).load();
            flyway.migrate();
        }catch (SQLException se){
            throw new IllegalStateException("Can't connect!",se);
        }
    }

    public void validateName(String name){
        if(name == null || name.isBlank()){
            throw new WrongDataInputException("A név megadása kötelező.");
        }
    }

    public String validateZipCodeAndGetCity(String zipCode){
        if(zipCode == null || zipCode.isBlank() || zipCode.length() != 4){
            throw new WrongDataInputException("Helytelen vagy üresen hagyott irányítószám. ".concat(zipCode));
        }
        return citiesRepository.getCityByZipCode(zipCode).getCityName();
    }

    public void validateZipCode(String zipCode){
        if(zipCode == null || zipCode.isBlank() || zipCode.length() != 4){
            throw new WrongDataInputException("Helytelen vagy üresen hagyott irányítószám: ".concat(zipCode));
        }
    }
    public void validateAge(int age) {
        if(age > 100 || age < 10){
            throw new WrongDataInputException("Helytelen életkor: " + age);
        }
    }

    public void validateEmail(String email) {
        if(email.isBlank() || email.length() < 3 || !email.contains("@")){
            throw new WrongDataInputException("Helytelen email: ".concat(email));
        }
    }

    public void emailChecker(String email,String emailCheck) {
        if (!email.equals(emailCheck)){
            throw new WrongDataInputException("Az emailcímek nem egyeznek meg: ".concat(email).concat(" ").concat(emailCheck));
        }
    }

    public void validateSscNumber(String sscNumber) {
        int counter = 0;
        for (int i = 0; i < sscNumber.length()-1; i+=2) {
            counter += Character.getNumericValue(sscNumber.charAt(i)) * 3;
            counter += Character.getNumericValue(sscNumber.charAt(i+1)) * 7;
        }
        if(counter % 10 != Character.getNumericValue(sscNumber.charAt(sscNumber.length()-1))){
            throw new WrongDataInputException("Helytelen tajszám: ".concat(sscNumber));
        }
    }

    public void insertCitizen(String name, String zipCode, int age, String email, String sscNumber) {
        citizensRepository.insertCitizen(name,zipCode,age,email,sscNumber);
    }

    public void insertCitizensFromFile(String path){
        List<String> wrongLines = new ArrayList<>();
        String line;
        try(BufferedReader br = Files.newBufferedReader(Path.of(path))){
            br.readLine();
            while((line=br.readLine())!=null){
                try {
                    processLine(line);
                } catch (WrongDataInputException wdie){
                    wrongLines.add(wdie.getMessage() + " " + line);
                }
            }
        }catch (IOException e) {
            throw new IllegalStateException("Can not reach file!");
        }

        if(!wrongLines.isEmpty()){
            writeWrongLines(wrongLines);
            System.out.println("A megadott fájl hibás adatokat tartalmaz.");
            System.out.println("A hibás adatok a következő fájlba lettek mentve: src/test/resources/citizensDataToCorrect.csv");
            System.out.println("A hibás adatokal rendelkező személyek nem lettek regisztrálva! Regisztrálás előtt javítsa az adatokat!");
        }
    }

    private void processLine(String line) throws WrongDataInputException {
        String[] temp = line.split(DELIMITER);
        validateName(temp[0]);
        validateZipCode(temp[1]);
        validateAge(Integer.parseInt(temp[2]));
        validateEmail(temp[3]);
        validateSscNumber(temp[4]);
        Citizen citizen = new Citizen(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3],temp[4]);
        citizensRepository.insertCitizen(citizen);
    }

    private void writeWrongLines(List<String> wrongLines){
        try {
            Files.write(Path.of("src/test/resources/citizensDataToCorrect.csv"),wrongLines);
        }catch (IOException ioe){
            throw new IllegalStateException("Cant write file",ioe);
        }
    }

    public void generateFile(String zipCode, String fileName) {
      List<Citizen> result = citizensRepository.getCitizensByZipCodeToVaccination(zipCode);
      List<String> toWrite = new ArrayList<>();
      toWrite.add(GENERATED_DOCUMENT_HEAD);
      LocalTime startTime = LocalTime.of(8, 0);

        for (Citizen actual: result) {
            toWrite.add(String.format("%s;%s;%s;%d;%s;%s",startTime,actual.getName(),actual.getZipCode(),actual.getAge(),actual.getEmail(),actual.getSocialSecurityNumber()));
            startTime = startTime.plusMinutes(30);
        }
        try {
            Files.write(Path.of(String.format("src/test/resources/%s.csv",fileName)),toWrite);
        }catch (IOException ioe){
            throw new IllegalStateException("Cant write file",ioe);
        }
    }

    public void vaccination(String sscNumber, LocalDateTime vaccinationTime, VaccinationType vaccinationType) {
        Citizen citizen = citizensRepository.getCitizenBySscNUmber(sscNumber);
        if(citizen.getLastVaccination().isAfter(vaccinationTime.minusDays(15))){
            throw new WrongDataInputException("Nem telt el a 15 nap a két oltás között!");
        }
        citizensRepository.updateCitizenVaccinationById(citizen.getId(),vaccinationTime);
        vaccinationsRepository.insertVaccination(citizen.getId(),vaccinationTime,VaccinationStatus.OK,"",vaccinationType);
        }

    public void rejectVaccination(String sscNumber, LocalDateTime vaccinationTime,String note){
                Citizen citizen = citizensRepository.getCitizenBySscNUmber(sscNumber);
                vaccinationsRepository.insertRejectedVaccination(citizen.getId(),vaccinationTime,VaccinationStatus.REJECTED,note);
        }

    public void validateVaccination(String sscNumber) {
        try {
            Citizen citizen = citizensRepository.getCitizenBySscNUmber(sscNumber);
            if(citizen.getNumberOfVaccination() >= 2) {
                throw new IllegalStateException("Már rendelkezik két oltással.");
            }
        }catch (IncorrectResultSizeDataAccessException exception){
            throw new WrongDataInputException("Nem regisztrált taj: " + sscNumber,exception);
        }
    }

    public Citizen getCitizenBySscNumber(String sscNumber){
        return citizensRepository.getCitizenBySscNUmber(sscNumber);
    }

    public Vaccination getVaccinationByCitizenId(Long citizenId){
        return vaccinationsRepository.getVaccinationByCitizenId(citizenId);
    }

}
