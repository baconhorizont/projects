package covid;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class VaccinationsRepository {

    private JdbcTemplate jdbcTemplate;

    public VaccinationsRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
