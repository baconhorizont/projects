package covid;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VaccinationsRepository {

    private JdbcTemplate jdbcTemplate;

    public VaccinationsRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long insertVaccination(Long citizenId, LocalDateTime vaccinationTime, VaccinationStatus status, String note, VaccinationType type){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement("insert into vaccinations (citizen_id, vaccination_date, `status`, note, vaccination_type) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1,citizenId);
                    ps.setTimestamp(2, Timestamp.valueOf(vaccinationTime));
                    ps.setString(3,status.toString());
                    ps.setString(4,note);
                    ps.setString(5,type.toString());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }


    public Long insertRejectedVaccination(Long citizenId, LocalDateTime vaccinationTime, VaccinationStatus status, String note){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement("insert into vaccinations (citizen_id, vaccination_date, `status`, note) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1,citizenId);
                    ps.setTimestamp(2, Timestamp.valueOf(vaccinationTime));
                    ps.setString(3,status.toString());
                    ps.setString(4,note);
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public Vaccination getVaccinationByCitizenId(Long citizenId){
        String sqlQuery = "SELECT * FROM vaccinations WHERE citizen_id = ? AND `status` = 'OK'";
        return jdbcTemplate.queryForObject(sqlQuery,(rs,rowNum) -> new Vaccination(rs.getLong("vaccination_id"),
                        rs.getLong("citizen_id"),
                        rs.getTimestamp("vaccination_date").toLocalDateTime(),
                        VaccinationStatus.valueOf(rs.getString("status")),
                        VaccinationType.valueOf(rs.getString("vaccination_type"))),
                citizenId);
    }

}