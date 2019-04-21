/**
 * @Author Andreas Dan Petersen
 * ShipRepo behandler READ af skibe fra databasen
 */

package com.kea.shipsandsails.Repository;

import com.kea.shipsandsails.Model.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Ship> fetchShips()
    {
        String sqlQuery = "SELECT * from ship";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Ship.class));
    }
}
