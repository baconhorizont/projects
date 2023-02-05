package covid;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CitiesRepository {

    private JdbcTemplate jdbcTemplate;

    public CitiesRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public City getCityByZipCode(String zipCode){
        String sqlQuery = "select * from cities where zip = ?";
        return jdbcTemplate.queryForObject(sqlQuery,(rs,rowNum) -> new City(rs.getLong("city_id"),
                rs.getString("zip"),
                rs.getString("city"),
                rs.getString("city_part")),
                zipCode);
    }

}
