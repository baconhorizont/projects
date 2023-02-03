package covid;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VaccinationsRepositoryTest {
    VaccinationsRepository vaccinationsRepository;
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

        vaccinationsRepository = new VaccinationsRepository(dataSource);
        citizensRepository = new CitizensRepository(dataSource);
        citizensRepository.insertCitizen("Bence","7030",66,"alma@alma.com","544252424");
        citizensRepository.insertCitizen("Kata","7030",25,"alma@alma.com","544252424");
        citizensRepository.insertCitizen("Tamás","7030",44,"alma@alma.com","544252424");

    }

    @Test
    void testInsertVaccination(){

        Long id = vaccinationsRepository.insertVaccination(1L, LocalDateTime.now(),VaccinationStatus.OK,"asd",VaccinationType.PFIZER);
        assertEquals(1L,id);
    }

}