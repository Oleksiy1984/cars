package ua.nure.orlovskyi.SummaryTask4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Gender;
import ua.nure.orlovskyi.SummaryTask4.model.Role;
import ua.nure.orlovskyi.SummaryTask4.model.User;

public class UserDAO extends GenericDAO<User> {
	
	
	private static final String BLOCK_USER = "UPDATE rental.users " + "SET users.blocked = true "
			+ "WHERE users.id = ?;";

	private static final String UNBLOCK_USER = "UPDATE rental.users " + "SET users.blocked = false "
			+ "WHERE users.id = ?;";
	private static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT rental.users.*, rental.roles.role_name FROM rental.users " +
			"INNER JOIN rental.roles " + 
			"ON rental.users.role=rental.roles.id " 
			+ "WHERE users.login=? AND users.password=? AND rental.roles.role_name IN ('Admin', 'Manager', 'Client') AND users.blocked = false;";

	private static final String INSERT_CLIENT = "INSERT INTO rental.users "
			+ "(login, password, name, email, mobile, gender, role, blocked, passport) "
			+ "VALUES (?, ?, ?, ?, ?, ?, 3, 0, ?);";
	
	private static final String INSERT_MANAGER = "INSERT INTO rental.users "
			+ "(login, password, name, email, mobile, gender, role, passport) "
			+ "VALUES (?, ?, ?, ?, ?, ?, 2, ?);";

	private static final String GET_ALL_USERS = "SELECT rental.users.*, rental.roles.role_name FROM rental.users " + 
			"INNER JOIN rental.roles " + 
			"ON rental.users.role=rental.roles.id;";
	
	private static final String GET_PASSPORT = "SELECT users.passport " + "FROM users " + "WHERE users.passport=?;";
	private static final String GET_LOGIN = "SELECT users.login " + "FROM users " + "WHERE users.login=?;";

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
	private final static String NAME = "User";

	public UserDAO(Connection con) {
		super(con, NAME);
	}

	public User getUserByCredentials(String uid, String pwd) {
		LOGGER.info("getUserByCredentials method call");
		User user = null;
		// see if there is a matching User
		try (PreparedStatement preparedStatement = con.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD)) {
			preparedStatement.setString(1, uid);
			preparedStatement.setString(2, pwd);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				user.setRole(Role.valueOf(rs.getString("role_name")));
				user.setGender(Gender.valueOf(rs.getString("gender")));
				user.setName(rs.getString("name"));
				user.setMobile(rs.getString("mobile"));
				user.setIsBlocked(rs.getBoolean("blocked"));
				user.setPassport(rs.getString("passport"));
				user.setEmail(rs.getString("email"));
			}
			return user;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return user;
		}
	}

	public Integer insertClient(User user) {
		LOGGER.info("insertClient method call");
		LOGGER.info(user.toString());
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_CLIENT,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getMobile());
			preparedStatement.setString(6, user.getGender().name());
			preparedStatement.setString(7, user.getPassport());
			result = preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
				} else {
					LOGGER.error("Creating user failed, no ID obtained.");
				}
			}
			return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
	}
	
	public Integer insertManager(User user) {
		LOGGER.info("insertManager method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_MANAGER,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getMobile());
			preparedStatement.setString(6, user.getGender().name());
			preparedStatement.setString(7, user.getPassport());
			result = preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
				} else {
					LOGGER.error("Creating user failed, no ID obtained.");
				}
			}
			return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
	}

	public List<User> getAllUsers() {
		LOGGER.info("getAllUsers method call");
		List<User> result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(GET_ALL_USERS)) {
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				result = new ArrayList<>();
				do {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setLogin(rs.getString("login"));
					user.setPassword(rs.getString("password"));
					user.setRole(Role.valueOf(rs.getString("role_name")));
					user.setGender(Gender.valueOf(rs.getString("gender")));
					user.setName(rs.getString("name"));
					user.setMobile(rs.getString("mobile"));
					user.setEmail(rs.getString("email"));
					user.setIsBlocked(rs.getBoolean("blocked"));
					user.setPassport(rs.getString("passport"));
					result.add(user);
				} while (rs.next());
			} else {
				// No data
			}
			return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}

	}

	public String getPassport(String passport) {
		LOGGER.info("getPassport method call");
		String result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(GET_PASSPORT)) {
			preparedStatement.setString(1, passport);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
	}

	public String getLogin(String login) {
		LOGGER.info("getLogin method call");
		String result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(GET_LOGIN)) {
			preparedStatement.setString(1, login);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
			return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
	}
	
	public Integer blockUser(Integer id) {
		LOGGER.info("blockUser method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(BLOCK_USER)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
			return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
	}
	
	public Integer unBlockUser(Integer id) {
		LOGGER.info("unBlockUser method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(UNBLOCK_USER)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
			return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
	}
	
	
}
