package ua.nure.orlovskyi.SummaryTask4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Brand;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.model.CarType;
import ua.nure.orlovskyi.SummaryTask4.model.Gender;
import ua.nure.orlovskyi.SummaryTask4.model.Order;
import ua.nure.orlovskyi.SummaryTask4.model.Role;
import ua.nure.orlovskyi.SummaryTask4.model.Transmission;
import ua.nure.orlovskyi.SummaryTask4.model.User;

public class OrderDAO extends GenericDAO<Order>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDAO.class);
	private final static String NAME = "Order";

	public OrderDAO(Connection con) {
		super(con, NAME);
	}
	
	
	private static final String DELETE_ORDER =
	"DELETE FROM rental.orders WHERE rental.orders.car_id = ?";
	
	private static final String REJECT_ORDER =
	"UPDATE rental.orders " + 
	"SET rental.orders.rejected = true, rental.orders.reason = ? " + 
	"WHERE rental.orders.id = ?;";
	
	private static final String GET_CURRENT_ORDERS =
	"SELECT rental.orders.*, rental.users.*, rental.cars.*, rental.roles.role_name FROM rental.orders " + 
	"INNER JOIN rental.cars " + 
	"ON rental.cars.id = rental.orders.car_id " + 
	"INNER JOIN rental.users " + 
	"ON rental.orders.client_id = rental.users.id " + 
	"INNER JOIN rental.roles " + 
	"ON rental.users.id = rental.roles.id " +
	"WHERE rental.cars.available = false AND rental.orders.end_date >= ?;";
	
	private static final String GET_ORDERS_NOT_APPROVED =
	"SELECT rental.orders.*, rental.cars.brand, " + 
	"rental.cars.model, rental.cars.car_type, rental.cars.color, rental.cars.price, rental.cars.available, " + 
	"rental.cars.photo, rental.cars.transmission, rental.users.login, rental.users.password, rental.users.blocked, " + 
	"rental.users.email, rental.users.gender, rental.users.mobile, rental.users.name, rental.users.passport, rental.roles.role_name " + 
	"FROM rental.orders " + 
	"INNER JOIN rental.cars " + 
	"ON rental.orders.car_id = rental.cars.id " + 
	"INNER JOIN rental.users " + 
	"ON rental.orders.client_id = rental.users.id " + 
	"INNER JOIN rental.roles " + 
	"ON rental.users.id = rental.roles.id " +
	"WHERE rental.orders.approved = false AND rental.orders.rejected = false " +
	"ORDER BY rental.orders.date_order;";
	
	private static final String SET_APPROVED =
			"UPDATE rental.orders " + 
			"SET rental.orders.approved = true " + 
			"WHERE rental.orders.id = ?;";
	
	private static final String INSERT_ORDER = "INSERT INTO rental.orders " + 
			"(rental.orders.pick_up_location, rental.orders.drop_off_location, rental.orders.date_order, " + 
			"rental.orders.start_date, rental.orders.end_date, rental.orders.client_id, rental.orders.car_id, " + 
			"rental.orders.driver, rental.orders.estimated_price, rental.orders.number_days) " + 
			"VALUES(?,?,?,?,?,?,?,?,?,?);";
	
	private static final String FIND_ORDER_BY_ID = 
	"SELECT rental.orders.*, rental.users.*, rental.cars.*, rental.roles.role_name FROM rental.orders " + 
	"INNER JOIN rental.cars " + 
	"ON rental.cars.id = rental.orders.car_id " + 
	"INNER JOIN rental.users " + 
	"ON rental.users.id = rental.orders.client_id " + 
	"INNER JOIN rental.roles " + 
	"ON rental.users.id = rental.roles.id " +
	"WHERE rental.orders.id = ?;";
	
	private static final String FIND_ORDER_BY_CLIENT_ID = 
			"SELECT rental.orders.*, "
			+ "(rental.orders.estimated_price + IFNULL(rental.accidents.loss, 0)) as Total, IFNULL(rental.accidents.loss, 0) as damage,"
			+ "rental.cars.brand, rental.cars.model, rental.cars.car_type, rental.cars.color, rental.cars.price, rental.cars.available, " + 
			"rental.cars.photo, rental.cars.transmission "
			+ "FROM rental.orders " + 
			"INNER JOIN rental.cars " + 
			"ON rental.cars.id = rental.orders.car_id " + 
			"LEFT JOIN rental.accidents " + 
			"ON rental.accidents.order_id = rental.orders.id " + 
			"WHERE rental.orders.client_id = ? " + 
			"ORDER BY rental.orders.date_order DESC;";
	
	public Order findOrderById(Integer id) {
		Order order = null;
		LOGGER.info("findOrderById method call");
		try(PreparedStatement preparedStatement = con.prepareStatement(FIND_ORDER_BY_ID)){
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				order = new Order();
				order.setId(resultSet.getInt(1));
				order.setPickUpLocation(resultSet.getString("pick_up_location"));
				order.setDropOffLocation(resultSet.getString("drop_off_location"));
				order.setDateTimeNow(resultSet.getTimestamp("date_order").toLocalDateTime());
				order.setPickUpDate(resultSet.getDate("start_date").toLocalDate());
				order.setDropOffDate(resultSet.getDate("end_date").toLocalDate());
				order.setIsDriver(resultSet.getBoolean("driver"));
				order.setIsApproved(resultSet.getBoolean("approved"));
				order.setReason(resultSet.getString("reason"));
				order.setEstimatedPrice(resultSet.getDouble("estimated_price"));
				order.setNumberDaysRent(resultSet.getInt("number_days"));
				order.setIsRejected(resultSet.getBoolean("rejected"));
				
				//set car
				Car car = new Car();
				car.setId(resultSet.getInt("car_id"));
				car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
				car.setColor(resultSet.getString("color"));
				car.setIsAvailable(resultSet.getBoolean("available"));
				car.setPhoto(resultSet.getString("photo"));
				car.setPrice(resultSet.getDouble("price"));
				car.setBrand(Brand.valueOf(resultSet.getString("brand")));
				car.setModel(resultSet.getString("model"));
				car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
				
				order.setCar(car);
				
				//set client
				User user = new User();
				user.setId(resultSet.getInt("client_id"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setRole(Role.valueOf(resultSet.getString("role_name")));
				user.setGender(Gender.valueOf(resultSet.getString("gender")));
				user.setName(resultSet.getString("name"));
				user.setMobile(resultSet.getString("mobile"));
				user.setIsBlocked(resultSet.getBoolean("blocked"));
				user.setPassport(resultSet.getString("passport"));
				
				order.setUser(user);
				
			}
		} catch (SQLException e) {
			LOGGER.error(e.toString());
		}
		return order;
	}
	public Integer insertOrder(Order order) {
		LOGGER.info(order.toString());
		LOGGER.info("insertOrder method call");
		Integer result = null;
		try(PreparedStatement preparedStatement = con.prepareStatement(INSERT_ORDER,  
				Statement.RETURN_GENERATED_KEYS)){
			
			preparedStatement.setString(1, order.getPickUpLocation());
			preparedStatement.setString(2, order.getDropOffLocation());
			preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			preparedStatement.setDate(4, Date.valueOf(order.getPickUpDate()));
			preparedStatement.setDate(5, Date.valueOf(order.getDropOffDate()));
			preparedStatement.setInt(6, order.getUser().getId());
			preparedStatement.setInt(7, order.getCar().getId());
			preparedStatement.setBoolean(8, order.getIsDriver());
			preparedStatement.setDouble(9, order.getEstimatedPrice());
			preparedStatement.setInt(10, order.getNumberDaysRent());
			result = preparedStatement.executeUpdate();
			if (result == 0) {
				LOGGER.error("Creating order failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                order.setId(generatedKeys.getInt(1));
	            }
	            else {
	            	LOGGER.error("Creating order failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		
		return result;
	}
	
	public List<Order> getAllOrdersNotApproved (){
		LOGGER.info("getAllOrdersNotApproved method call");
		List<Order> result = null;
		try(PreparedStatement preparedStatement = con.prepareStatement(GET_ORDERS_NOT_APPROVED)){
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result= new ArrayList<>();
			    do {
			    	Order order = new Order();
					order.setId(resultSet.getInt(1));
					order.setPickUpLocation(resultSet.getString("pick_up_location"));
					order.setDropOffLocation(resultSet.getString("drop_off_location"));
					order.setDateTimeNow(resultSet.getTimestamp("date_order").toLocalDateTime());
					order.setPickUpDate(resultSet.getDate("start_date").toLocalDate());
					order.setDropOffDate(resultSet.getDate("end_date").toLocalDate());
					order.setIsDriver(resultSet.getBoolean("driver"));
					order.setIsApproved(resultSet.getBoolean("approved"));
					order.setReason(resultSet.getString("reason"));
					order.setEstimatedPrice(resultSet.getDouble("estimated_price"));
					order.setNumberDaysRent(resultSet.getInt("number_days"));
					order.setIsRejected(resultSet.getBoolean("rejected"));
					//set car
					Car car = new Car();
					car.setId(resultSet.getInt("car_id"));
					car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
					car.setColor(resultSet.getString("color"));
					car.setIsAvailable(resultSet.getBoolean("available"));
					car.setPhoto(resultSet.getString("photo"));
					car.setPrice(resultSet.getDouble("price"));
					car.setBrand(Brand.valueOf(resultSet.getString("brand")));
					car.setModel(resultSet.getString("model"));
					car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
					
					order.setCar(car);
					
					//set client
					User user = new User();
					user.setId(resultSet.getInt("client_id"));
					user.setLogin(resultSet.getString("login"));
					user.setPassword(resultSet.getString("password"));
					user.setRole(Role.valueOf(resultSet.getString("role_name")));
					user.setGender(Gender.valueOf(resultSet.getString("gender")));
					user.setName(resultSet.getString("name"));
					user.setMobile(resultSet.getString("mobile"));
					user.setIsBlocked(resultSet.getBoolean("blocked"));
					user.setPassport(resultSet.getString("passport"));
					
					order.setUser(user);
					
					result.add(order);
			    } while(resultSet.next());
			} 
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return result;
	}
	
	public Integer setApproved(Integer id) {
		LOGGER.info("setApproved method call");
		Integer result = null;
		try(PreparedStatement preparedStatement = con.prepareStatement(SET_APPROVED)){
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return result;
		
	}
	
	public Integer rejectOrder(Order order, String comment) {
		LOGGER.info("rejectOrder method call");
		Integer result = null;
		try(PreparedStatement preparedStatement = con.prepareStatement(REJECT_ORDER)){
			preparedStatement.setString(1, comment);
			preparedStatement.setInt(2, order.getId());
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
		return result;
	}
	
	public List<Order> getCurrentOrders (){
		LOGGER.info("getCurrentOrders method call");
		List<Order> result = null;
		try(PreparedStatement preparedStatement = con.prepareStatement(GET_CURRENT_ORDERS)){
			java.util.Date date = new java.util.Date();
			preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result= new ArrayList<>();
			    do {
			    	Order order = new Order();
					order.setId(resultSet.getInt(1));
					order.setPickUpLocation(resultSet.getString("pick_up_location"));
					order.setDropOffLocation(resultSet.getString("drop_off_location"));
					order.setDateTimeNow(resultSet.getTimestamp("date_order").toLocalDateTime());
					order.setPickUpDate(resultSet.getDate("start_date").toLocalDate());
					order.setDropOffDate(resultSet.getDate("end_date").toLocalDate());
					order.setIsDriver(resultSet.getBoolean("driver"));
					order.setIsApproved(resultSet.getBoolean("approved"));
					order.setReason(resultSet.getString("reason"));
					order.setEstimatedPrice(resultSet.getDouble("estimated_price"));
					order.setNumberDaysRent(resultSet.getInt("number_days"));
					order.setIsRejected(resultSet.getBoolean("rejected"));
					//set car
					Car car = new Car();
					car.setId(resultSet.getInt(15));
					car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
					car.setColor(resultSet.getString("color"));
					car.setIsAvailable(resultSet.getBoolean("available"));
					car.setPhoto(resultSet.getString("photo"));
					car.setPrice(resultSet.getDouble("price"));
					car.setBrand(Brand.valueOf(resultSet.getString("brand")));
					car.setModel(resultSet.getString("model"));
					car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
					
					order.setCar(car);
					
					//set client
					User user = new User();
					user.setId(resultSet.getInt("client_id"));
					user.setLogin(resultSet.getString("login"));
					user.setPassword(resultSet.getString("password"));
					user.setRole(Role.valueOf(resultSet.getString("role_name")));
					user.setGender(Gender.valueOf(resultSet.getString("gender")));
					user.setName(resultSet.getString("name"));
					user.setMobile(resultSet.getString("mobile"));
					user.setIsBlocked(resultSet.getBoolean("blocked"));
					user.setPassport(resultSet.getString("passport"));
					
					order.setUser(user);
					
					result.add(order);
			    } while(resultSet.next());
			} else {
			    // No data
			}
			return result;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return result;
		}
	}
	public Integer deleteOrderByCarId(Integer id) {
		LOGGER.info("deleteOrder method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_ORDER)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return result;
	}
	
	public List<Order> getOrdersByClientId(Integer id) {
		List<Order> result = null;
		LOGGER.info("getOrdersByClientId method call");
		try(PreparedStatement preparedStatement = con.prepareStatement(FIND_ORDER_BY_CLIENT_ID)){
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result= new ArrayList<>();
			    do {
				Order order = new Order();
				order.setId(resultSet.getInt("id"));
				order.setPickUpLocation(resultSet.getString("pick_up_location"));
				order.setDropOffLocation(resultSet.getString("drop_off_location"));
				order.setDateTimeNow(resultSet.getTimestamp("date_order").toLocalDateTime());
				order.setPickUpDate(resultSet.getDate("start_date").toLocalDate());
				order.setDropOffDate(resultSet.getDate("end_date").toLocalDate());
				order.setIsDriver(resultSet.getBoolean("driver"));
				order.setIsApproved(resultSet.getBoolean("approved"));
				order.setReason(resultSet.getString("reason"));
				order.setEstimatedPrice(resultSet.getDouble("estimated_price"));
				order.setNumberDaysRent(resultSet.getInt("number_days"));
				order.setIsRejected(resultSet.getBoolean("rejected"));
				order.setTotalPrice(resultSet.getDouble("Total"));
				order.setDamage(resultSet.getDouble("damage"));
				LOGGER.info(order.getDamage().toString());
				
				//set car
				Car car = new Car();
				car.setId(resultSet.getInt("car_id"));
				car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
				car.setColor(resultSet.getString("color"));
				car.setIsAvailable(resultSet.getBoolean("available"));
				car.setPhoto(resultSet.getString("photo"));
				car.setPrice(resultSet.getDouble("price"));
				car.setBrand(Brand.valueOf(resultSet.getString("brand")));
				car.setModel(resultSet.getString("model"));
				car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
				order.setCar(car);
				result.add(order);
			    } while(resultSet.next());
			} 
		} catch (SQLException e) {
			LOGGER.error(e.toString());
		}
		return result;
	}
}
