package covid;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CitizensRepositoryTest {

    CitizensRepository citizensRepository;

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

        citizensRepository = new CitizensRepository(dataSource);

    }

    @Test
    void testInsertCitizen(){

        Long id = citizensRepository.insertCitizen("Kata","7030",25,"kata@gmail.com","873668046");

        assertEquals(1L,id);
    }

    @Test
    void testGetCitizensByZipCodeToVaccination(){
        citizensRepository.insertCitizen("Kata","7030",25,"kata@gmail.com","873668046");
        citizensRepository.insertCitizen("Bence","7030",84,"kata@gmail.com","873668046");
        citizensRepository.insertCitizen("Áron","7030",32,"kata@gmail.com","873668046");
        citizensRepository.insertCitizen("Kata","2222",25,"kata@gmail.com","873668046");
        citizensRepository.insertCitizen("Kata","1111",25,"kata@gmail.com","873668046");
      List<Citizen> result =  citizensRepository.getCitizensByZipCodeToVaccination("7030");

      assertEquals(84,result.get(0).getAge());
    }

    @Test
    void testGetCitizenBySscNUmber(){
        citizensRepository.insertCitizen("Kata","7030",25,"kata@gmail.com","873668046");
        citizensRepository.insertCitizen("Bence","7030",84,"bence@gmail.com","613315412");

        Citizen citizen = citizensRepository.getCitizenBySscNUmber("613315412");

        assertEquals(2L,citizen.getId());

    }

    @Test
    void testUpdateCitizenVaccinationById(){
        citizensRepository.insertCitizen("Kata","7030",25,"kata@gmail.com","873668046");
        citizensRepository.insertCitizen("Bence","7030",84,"bence@gmail.com","613315412");

        citizensRepository.updateCitizenVaccinationById(2L, LocalDateTime.now());

    }


}