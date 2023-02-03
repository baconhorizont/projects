package covid;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VaccinationServiceTest {

    Flyway flyway;
    @BeforeEach
    void init() {

        MariaDbDataSource dataSource = new MariaDbDataSource();
        try{
            dataSource.setUrl("jdbc:mariadb://localhost:3306/covid?useUnicode=true");
            dataSource.setUser("covid");
            dataSource.setPassword("covid");
        }catch (SQLException se){
            throw new IllegalStateException("Cannot connect!",se);
        }

        flyway = Flyway.configure().cleanDisabled(false).dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();


    }

    VaccinationService vaccinationService = new VaccinationService();

    @Test
    void testValidateSscNumber(){
        vaccinationService.validateSscNumber("830546657");
    }

    @Test
    void testInvalidSscNumber(){
        WrongDataInputException wdie = assertThrows(WrongDataInputException.class,
                () -> vaccinationService.validateSscNumber("830546654"));

        assertEquals("Helytelen tajszám: 830546654", wdie.getMessage());
    }

    @Test
    void testInsertCitizensFromFile(){
        vaccinationService.insertCitizensFromFile("src/test/resources/citizens.csv");
    }

    @Test
    void testInsertCitizensFromFileWrongPath(){
        IllegalStateException ise = assertThrows(IllegalStateException.class,
                () -> vaccinationService.insertCitizensFromFile("asd"));

        assertEquals("Can not reach file!",ise.getMessage());

    }

    @Test
    void testInsertCitizensFromFileWrongData() throws IOException {
        vaccinationService.insertCitizensFromFile("src/test/resources/citizensWrongInputData.csv");

        assertEquals(List.of("Helytelen email: lriddel2phoca.cz Lynn Riddel;2173;71;lriddel2phoca.cz;656603664",
                "Helytelen tajszám: 0300172 Augustus Passman;2194;32;apassmanb@slate.com;0300172",
                "Helytelen életkor: 2000 Herbie Durnall;2194;2000;hdurnalld@usda.gov;283120121"),
                Files.readAllLines(Path.of("src/test/resources/citizensDataToCorrect.csv")));

    }

    @Test
    void testGetCitizensByZipCodeToVaccination(){
        vaccinationService.insertCitizensFromFile("src/test/resources/citizens.csv");

        vaccinationService.generateFile("2194","dailyVaccination");

    }

    @Test
    void testVaccination(){
        vaccinationService.insertCitizensFromFile("src/test/resources/citizens.csv");

        vaccinationService.vaccination("723662833", LocalDateTime.of(2022,3,14,8,0,0),VaccinationType.MODERNA);

    }

    @Test
    void testRejectVaccination(){
        vaccinationService.insertCitizensFromFile("src/test/resources/citizens.csv");

        vaccinationService.rejectVaccination("723662833",LocalDateTime.of(2021,5,14,10,0,0),
                "The patient had a fever.");
    }
}