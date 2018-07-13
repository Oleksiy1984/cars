package ua.nure.orlovskyi.SummaryTask4.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Brand;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.model.CarType;
import ua.nure.orlovskyi.SummaryTask4.model.Transmission;

public class CarDAO extends GenericDAO<Car> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CarDAO.class);
	private final static String NAME = "Car";

	public CarDAO(Connection con) {
		super(con, NAME);
	}

	private static final String UPDATE_CAR_PHOTO  =
	"UPDATE rental.cars SET photo = ? WHERE id = ?;";
	
	private static final String SELECT_CARS_AVAILABLE = "SELECT * " + "FROM rental.cars " + "WHERE available = true;";

	private static final String SELECT_CARS_UNAVAILABLE = "SELECT * " + "FROM rental.cars "
			+ "WHERE available = false;";

	private static final String SELECT_ALL_CARS = "SELECT * " + "FROM rental.cars;";
	private static final String SELECT_BY_BRAND = "SELECT * " + "FROM rental.cars " + "WHERE rental.brand = ?;";
	private static final String SELECT_BY_TYPE = "SELECT * " + "FROM rental.cars " + "WHERE rental.cars.car_type = ?;";
	private static final String SELECT_BY_ID = "SELECT * " + "FROM rental.cars " + "WHERE rental.cars.id = ?;";
	private static final String SELECT_CARS_AVAILABLE_AT_DATE = "SELECT cars.id, cars.brand, cars.model, cars.car_type, cars.color, cars.price, cars.available, cars.photo, cars.transmission "
			+ "FROM rental.cars " + "WHERE available = true " + "UNION "
			+ "SELECT cars.id, cars.brand, cars.model, cars.car_type, cars.color, cars.price, cars.available, cars.photo, cars.transmission "
			+ "FROM rental.cars " + "INNER JOIN rental.orders " + "ON rental.cars.id = rental.orders.car_id "
			+ "where cars.available = false AND " + 
			"(rental.orders.start_date > ? or " + 
			"(rental.orders.end_date < ? " + 
			"AND rental.orders.end_date> ?));";
	
	private static final String SELECT_CARS_IN_DESIRED_TIME_FRAME = 
			"SELECT distinct cars.id "
			+ "FROM rental.cars " + "INNER JOIN rental.orders " + "ON rental.cars.id = rental.orders.car_id "
			+ "where cars.available = false AND rental.orders.end_date > ? "
			+ "AND NOT " + 
			"(rental.orders.start_date > ? or " + 
			"rental.orders.end_date < ?);";

	private static final String RESERVE_CAR = "UPDATE rental.cars " + "SET available = false "
			+ "WHERE rental.cars.id = ?;";

	private static final String RETURN_CAR = "UPDATE rental.cars " + "SET available = true "
			+ "WHERE rental.cars.id = ?;";

	private static final String INSERT_CAR =
	"INSERT INTO rental.cars " + 
	"(brand, model, car_type, color, price, transmission) " + 
	"VALUES (?, ?, ?, ?, ?, ?);";
	
	private static final String REMOVE_CAR =
	"DELETE FROM rental.cars WHERE rental.cars.id = ?;";
	
	private static final String UPDATE_CAR =
	"UPDATE rental.cars SET brand = ?, model = ?, car_type = ?, color = ?, price = ?, " + 
	"transmission = ? WHERE id = ?;";
	
	
	public Integer insertCar(Car car) {
		LOGGER.info("insertCar method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_CAR,
				Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, car.getBrand().name());
			preparedStatement.setString(2, car.getModel());
			preparedStatement.setString(3, car.getCarType().name());
			preparedStatement.setString(4, car.getColor());
			preparedStatement.setDouble(5, car.getPrice());
			preparedStatement.setString(6, car.getTransmission().name());
			result = preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					car.setId(generatedKeys.getInt(1));
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
	public List<Car> getAllAvailableCars() {
		LOGGER.info("getAllAvailableCars method call");
		List<Car> result = null;
		try (Statement statement = con.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_CARS_AVAILABLE)) {
			if (resultSet.next()) {
				result = new ArrayList<>();
				do {
					Car car = new Car();

					// set Car
					car.setId(resultSet.getInt("id"));
					car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
					car.setColor(resultSet.getString("color"));
					car.setIsAvailable(resultSet.getBoolean("available"));
					car.setPhoto(resultSet.getString("photo"));
					car.setPrice(resultSet.getDouble("price"));
					car.setBrand(Brand.valueOf(resultSet.getString("brand")));
					car.setModel(resultSet.getString("model"));
					car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
					result.add(car);
				} while (resultSet.next());
			} else {
				// No data
			}
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage());
		}
		return result;
	}

	public List<Car> getAllUnAvailableCars() {
		LOGGER.info("getAllUnAvailableCars method call");
		List<Car> result = null;
		try (Statement statement = con.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_CARS_UNAVAILABLE)) {
			if (resultSet.next()) {
				result = new ArrayList<>();
				do {
					Car car = new Car();

					// set Car
					car.setId(resultSet.getInt("id"));
					car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
					car.setColor(resultSet.getString("color"));
					car.setIsAvailable(resultSet.getBoolean("available"));
					car.setPhoto(resultSet.getString("photo"));
					car.setPrice(resultSet.getDouble("price"));
					car.setBrand(Brand.valueOf(resultSet.getString("brand")));
					car.setModel(resultSet.getString("model"));
					car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
					result.add(car);
				} while (resultSet.next());
			} else {
				// No data
			}
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage());
		}
		return result;
	}

	public List<Car> getAllAvailableCarsAtDate(LocalDate pickUpDate, LocalDate dropOff) {
		LOGGER.info("getAllAvailableCarsAtDate method call");
		List<Car> result = null;
		try (PreparedStatement statement = con.prepareStatement(SELECT_CARS_AVAILABLE_AT_DATE)) {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();

			statement.setDate(1, Date.valueOf(dropOff));
			statement.setDate(2, Date.valueOf(pickUpDate));
			statement.setString(3, dtf.format(localDate));
			

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = new ArrayList<>();
				do {
					Car car = new Car();

					// set Car
					car.setId(resultSet.getInt("id"));
					car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
					car.setColor(resultSet.getString("color"));
					car.setIsAvailable(resultSet.getBoolean("available"));
					car.setPhoto(resultSet.getString("photo"));
					car.setPrice(resultSet.getDouble("price"));
					car.setBrand(Brand.valueOf(resultSet.getString("brand")));
					car.setModel(resultSet.getString("model"));
					car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
					result.add(car);
				} while (resultSet.next());
			} else {
				// No data
			}
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage());
		}
		return result;
	}
	
	public List<Integer> getCarsInDesiredTimeFrame(LocalDate pickUpDate, LocalDate dropOff) {
		LOGGER.info("getAllAvailableCarsAtDate method call");
		List<Integer> result = null;
		try (PreparedStatement statement = con.prepareStatement(SELECT_CARS_IN_DESIRED_TIME_FRAME)) {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();

			statement.setString(1, dtf.format(localDate));
			statement.setDate(2, Date.valueOf(dropOff));
			statement.setDate(3, Date.valueOf(pickUpDate));
			
			

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = new ArrayList<>();
				do {
					Integer id = resultSet.getInt("id");
					result.add(id);
				} while (resultSet.next());
			} 
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage());
		}
		System.out.println(result);
		return result;
	}

	public List<Car> getAllCars() {
		LOGGER.info("getAllCars method call");
		List<Car> result = null;
		try (Statement statement = con.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL_CARS)) {

			if (resultSet.next()) {
				result = new ArrayList<>();
				do {
					Car car = new Car();

					// set Car
					car.setId(resultSet.getInt("id"));
					car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
					car.setColor(resultSet.getString("color"));
					car.setIsAvailable(resultSet.getBoolean("available"));
					car.setPhoto(resultSet.getString("photo"));
					car.setPrice(resultSet.getDouble("price"));
					car.setBrand(Brand.valueOf(resultSet.getString("brand")));
					car.setModel(resultSet.getString("model"));
					car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
					result.add(car);
				} while (resultSet.next());
			} else {
				// No data
			}
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage());
		}
		return result;
	}

	public List<Car> findByBrand(Brand brand) {
		LOGGER.info("findByBrand method call");
		List<Car> result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_BY_BRAND)) {
			preparedStatement.setString(1, brand.name());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				result = new ArrayList<>();
				do {
					Car car = new Car();

					// set Car
					car.setId(resultSet.getInt("id"));
					car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
					car.setColor(resultSet.getString("color"));
					car.setIsAvailable(resultSet.getBoolean("available"));
					car.setPhoto(resultSet.getString("photo"));
					car.setPrice(resultSet.getDouble("price"));
					car.setBrand(Brand.valueOf(resultSet.getString("brand")));
					car.setModel(resultSet.getString("model"));
					car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
					result.add(car);

				} while (resultSet.next());
			} else {
				// No data
			}
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage());
		}
		return result;
	}

	public List<Car> findByType(CarType carType) {
		LOGGER.info("findByType method call");
		List<Car> result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_BY_TYPE)) {
			preparedStatement.setString(1, carType.name());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				result = new ArrayList<>();
				do {
					Car car = new Car();

					// set Car
					car.setId(resultSet.getInt("id"));
					car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
					car.setColor(resultSet.getString("color"));
					car.setIsAvailable(resultSet.getBoolean("available"));
					car.setPhoto(resultSet.getString("photo"));
					car.setPrice(resultSet.getDouble("price"));
					car.setBrand(Brand.valueOf(resultSet.getString("brand")));
					car.setModel(resultSet.getString("model"));
					car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));

					result.add(car);
				} while (resultSet.next());
			} else {
				// No data
			}
		} catch (SQLException ex) {
			LOGGER.error(ex.getMessage());
		}
		return result;
	}

	public Car getCarById(Integer id) {
		LOGGER.info("getCarById method call");
		Car car = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_BY_ID)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				car = new Car();

				// set Car
				car.setId(resultSet.getInt("id"));
				car.setCarType(CarType.valueOf(resultSet.getString("car_type")));
				car.setColor(resultSet.getString("color"));
				car.setIsAvailable(resultSet.getBoolean("available"));
				car.setPhoto(resultSet.getString("photo"));
				car.setPrice(resultSet.getDouble("price"));
				car.setBrand(Brand.valueOf(resultSet.getString("brand")));
				car.setModel(resultSet.getString("model"));
				car.setTransmission(Transmission.valueOf(resultSet.getString("transmission")));
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return car;

	}

	public Integer reserveCar(Car car) {
		LOGGER.info("reserveCar method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(RESERVE_CAR)) {
			preparedStatement.setInt(1, car.getId());
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return result;

	}
	
	public Integer updateCarPhoto(Car car) {
		LOGGER.info("updateCarPhoto method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_CAR_PHOTO)) {
			preparedStatement.setString(1, car.getPhoto());
			preparedStatement.setInt(2, car.getId());
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return result;

	}

	public Integer returnCar(Integer id) {
		LOGGER.info("returnCar method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(RETURN_CAR)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return result;
	}
	public Integer deleteCar(Integer id) {
		LOGGER.info("deleteCar method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(REMOVE_CAR)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return result;
	}
	
	public Integer updateCar(Car car) {
		LOGGER.info("updateCar method call");
		Integer result = null;
		try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_CAR)) {
			preparedStatement.setString(1, car.getBrand().name());
			preparedStatement.setString(2, car.getModel());
			preparedStatement.setString(3, car.getCarType().name());
			preparedStatement.setString(4, car.getColor());
			preparedStatement.setDouble(5, car.getPrice());
			preparedStatement.setString(6, car.getTransmission().name());
			preparedStatement.setInt(7, car.getId());
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return result;
	}
}
