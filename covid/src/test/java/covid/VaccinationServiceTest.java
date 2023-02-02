package covid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VaccinationServiceTest {

    VaccinationService vaccinationService = new VaccinationService();

    @Test
    void testValidateSscNumber(){
        vaccinationService.validateSscNumber("830546657");
    }

    @Test
    void testInvalidSscNumber(){
        WrongDataInputException wdie = assertThrows(WrongDataInputException.class,
                () -> vaccinationService.validateSscNumber("830546654"));

        assertEquals("Helytelen tajszám.", wdie.getMessage());
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
    void testInsertCitizensFromFileWrongData(){
        WrongDataInputException wdie = assertThrows(WrongDataInputException.class,
                () -> vaccinationService.insertCitizensFromFile("src/test/resources/citizensWrongData.csv"));

        assertEquals("Helytelen email: lriddel2phoca.cz", wdie.getMessage());
    }


}