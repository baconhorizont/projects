package covid;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CitiesRepositoryTest {

    CitiesRepository citiesRepository;

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

        citiesRepository = new CitiesRepository(dataSource);

    }

    @Test
    void testGetCityByZip(){

        City city = citiesRepository.getCityByZipCode("2073");
        assertEquals("Tök",city.getCityName());
        assertNull(city.getCityPartName());

        City cityWithPartName = citiesRepository.getCityByZipCode("2484");
        assertEquals("Agárd",cityWithPartName.getCityPartName());
    }

}