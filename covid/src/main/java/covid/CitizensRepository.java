package covid;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class CitizensRepository {

    private static final int MAX_VACCINATION = 2;
    private static final int MIN_DAY_BETWEEN_VACCINATION = 15;
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public CitizensRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public Long insertCitizen(String name, String zipCode, int age, String email, String sscNumber){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement("insert into citizens (citizen_name, zip, age, email, taj) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1,name);
                    ps.setString(2,zipCode);
                    ps.setInt(3,age);
                    ps.setString(4,email);
                    ps.setString(5,sscNumber);
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public Long insertCitizen(Citizen citizen){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement("insert into citizens (citizen_name, zip, age, email, taj) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1,citizen.getName());
                    ps.setString(2,citizen.getZipCode());
                    ps.setInt(3,citizen.getAge());
                    ps.setString(4, citizen.getEmail());
                    ps.setString(5, citizen.getSocialSecurityNumber());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public List<Citizen> getCitizensByZipCodeToVaccination(String zipCode){
        String sqlQuery = "SELECT * FROM citizens WHERE zip = ? AND number_of_vaccinations < ? AND last_vaccination is NULL OR last_vaccination < ? ORDER BY age DESC,citizen_name ASC LIMIT 16";
        return jdbcTemplate.query(sqlQuery,(rs,rowNum) -> new Citizen(rs.getLong("citizen_id"), rs.getString("citizen_name")
                        ,rs.getString("zip"),rs.getInt("age"), rs.getString("email"), rs.getString("taj"),
                        rs.getInt("number_of_vaccinations")),
                        zipCode,MAX_VACCINATION,Timestamp.valueOf(LocalDateTime.now().minusDays(MIN_DAY_BETWEEN_VACCINATION)));
    }

    public Citizen getCitizenBySscNUmber(String sscNUmber){
        String sqlQuery = "SELECT * FROM citizens WHERE taj = ?";
        return jdbcTemplate.queryForObject(sqlQuery,(rs,rowNum) -> new Citizen(rs.getLong("citizen_id"), rs.getString("citizen_name")
                        ,rs.getString("zip"),rs.getInt("age"), rs.getString("email"), rs.getString("taj"),
                        rs.getInt("number_of_vaccinations"), rs.getTimestamp("last_vaccination").toLocalDateTime())
                ,sscNUmber);
    }

    public void updateCitizenVaccinationById(Long id,LocalDateTime vaccinationTime){
        String sqlQuery = "UPDATE citizens SET last_vaccination = ?, number_of_vaccinations = number_of_vaccinations + 1 WHERE citizen_id = ?";
        jdbcTemplate.update(sqlQuery,Timestamp.valueOf(vaccinationTime),id);
    }

}

