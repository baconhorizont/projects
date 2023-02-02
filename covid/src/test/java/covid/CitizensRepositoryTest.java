package covid;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

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


}