package ua.nure.orlovskyi.SummaryTask4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.model.Accident;

public class AccidentDAO extends GenericDAO<Accident>{

	private static final Logger LOGGER = LoggerFactory.getLogger(AccidentDAO.class);
	private final static String NAME = "Accident";

	public AccidentDAO(Connection con) {
		super(con, NAME);
	}

	private static final String INSERT_ACCIDENT = "INSERT INTO rental.accidents " + 
			"(order_id, loss, comment) " + 
			"VALUES (?,?,?);";

	public Integer addAccident(Accident accident) {
		LOGGER.info("addAccident method call");
		Integer result = null;
		try(PreparedStatement preparedStatement = con.prepareStatement(INSERT_ACCIDENT,  
				Statement.RETURN_GENERATED_KEYS)){
			preparedStatement.setInt(1, accident.getOrder().getId());
			preparedStatement.setDouble(2, accident.getLoss());
			preparedStatement.setString(3, accident.getComment());
			result = preparedStatement.executeUpdate();

	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                accident.setId(generatedKeys.getInt(1));
	            }
	            else {
	            	LOGGER.error("Creating order failed, no ID obtained.");
	            }
	        }
	        return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
	}

}
