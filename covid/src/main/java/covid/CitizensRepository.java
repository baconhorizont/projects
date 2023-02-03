package covid;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CitizensRepository {
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
//    public void insertCitizen(Citizen citizen,WrongDataInputException... wdie){
//        try (Connection conn = dataSource.getConnection()) {
//            conn.setAutoCommit(false);
//            try (PreparedStatement ps = conn.prepareStatement("insert into citizens (citizen_name, zip, age, email, taj) values(?,?,?,?,?)")){
//                if (wdie.length != 0){
//                    throw new IllegalArgumentException();
//                }
//                    ps.setString(1, citizen.getName());
//                    ps.setString(2,citizen.getZipCode());
//                    ps.setInt(3,citizen.getAge());
//                    ps.setString(4,citizen.getEmail());
//                    ps.setString(5,citizen.getSocialSecurityNumber());
//                    ps.executeUpdate();
//                conn.commit();
//            } catch (IllegalArgumentException iae){
//                conn.rollback();
//            }
//        } catch (SQLException sqle){
//            throw new IllegalStateException("Save failed!",sqle);
//        }
//    }

}

