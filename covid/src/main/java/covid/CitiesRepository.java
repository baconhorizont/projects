package covid;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CitiesRepository {

    private JdbcTemplate jdbcTemplate;

    public CitiesRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public City getCityByZipCode(String zipCode){
        return jdbcTemplate.queryForObject("select * from cities where zip = ?",
                (rs,rowNum) -> new City(rs.getLong("city_id"), rs.getString("zip")
                        ,rs.getString("city"), rs.getString("city_part")),zipCode);
    }

}
