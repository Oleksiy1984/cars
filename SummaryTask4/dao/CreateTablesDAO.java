package ua.nure.orlovskyi.SummaryTask4.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.model.Car;

public class CreateTablesDAO extends GenericDAO<Car> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateTablesDAO.class);
	private final static String NAME = "Create";

	public CreateTablesDAO(Connection con) {
		super(con, NAME);
	}

	private static final String CREATE_CARS = "CREATE TABLE `cars` (\r\n"
			+ "  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `brand` varchar(45) NOT NULL,\r\n"
			+ "  `model` varchar(45) NOT NULL,\r\n" + "  `car_type` varchar(45) NOT NULL,\r\n"
			+ "  `color` varchar(45) NOT NULL,\r\n" + "  `price` decimal(10,2) NOT NULL,\r\n"
			+ "  `available` tinyint(1) NOT NULL DEFAULT '1',\r\n"
			+ "  `photo` varchar(45) DEFAULT 'images/car1.jpg',\r\n" + "  `transmission` varchar(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`id`)\r\n" + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;\r\n";

	private static final String CREATE_ROLES = "CREATE TABLE `roles` (\r\n"
			+ "  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `role_name` varchar(45) NOT NULL,\r\n"
			+ "  PRIMARY KEY (`id`)\r\n" + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;\r\n";

	private static final String CREATE_USER = "CREATE TABLE `users` (\r\n"
			+ "  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `login` varchar(45) NOT NULL,\r\n"
			+ "  `password` varchar(100) NOT NULL,\r\n" + "  `name` varchar(45) NOT NULL,\r\n"
			+ "  `email` varchar(45) DEFAULT NULL,\r\n" + "  `mobile` varchar(45) NOT NULL,\r\n"
			+ "  `gender` enum('MALE','FEMALE') DEFAULT NULL,\r\n" + "  `role` int(11) NOT NULL,\r\n"
			+ "  `blocked` tinyint(1) DEFAULT '0',\r\n" + "  `passport` varchar(45) DEFAULT NULL,\r\n"
			+ "  PRIMARY KEY (`id`),\r\n" + "  UNIQUE KEY `login_UNIQUE` (`login`),\r\n"
			+ "  UNIQUE KEY `passport_UNIQUE` (`passport`),\r\n" + "  KEY `role_idx` (`role`),\r\n"
			+ "  CONSTRAINT `role` FOREIGN KEY (`role`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\r\n"
			+ ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;\r\n" + "";
	private static final String CREATE_ORDERS = "CREATE TABLE `orders` (\r\n"
			+ "  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n" + "  `pick_up_location` varchar(45) NOT NULL,\r\n"
			+ "  `drop_off_location` varchar(45) NOT NULL,\r\n" + "  `date_order` datetime NOT NULL,\r\n"
			+ "  `start_date` date NOT NULL,\r\n" + "  `end_date` date NOT NULL,\r\n"
			+ "  `client_id` int(11) NOT NULL,\r\n" + "  `car_id` int(11) NOT NULL,\r\n"
			+ "  `driver` tinyint(1) NOT NULL DEFAULT '0',\r\n" + "  `approved` tinyint(1) NOT NULL DEFAULT '0',\r\n"
			+ "  `reason` varchar(45) DEFAULT NULL,\r\n" + "  `estimated_price` decimal(10,2) NOT NULL,\r\n"
			+ "  `number_days` int(11) NOT NULL,\r\n" + "  `rejected` tinyint(1) NOT NULL DEFAULT '0',\r\n"
			+ "  PRIMARY KEY (`id`),\r\n" + "  KEY `client_id_idx` (`client_id`),\r\n"
			+ "  KEY `car_id_idx` (`car_id`),\r\n"
			+ "  CONSTRAINT `car_id` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\r\n"
			+ "  CONSTRAINT `client_id` FOREIGN KEY (`client_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\r\n"
			+ ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;\r\n";

	private static final String CREATE_ACCIDENTS =
	"CREATE TABLE `accidents` (\r\n" + 
	"  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n" + 
	"  `order_id` int(11) NOT NULL,\r\n" + 
	"  `loss` decimal(10,2) NOT NULL,\r\n" + 
	"  `comment` varchar(45) NOT NULL,\r\n" + 
	"  PRIMARY KEY (`id`),\r\n" + 
	"  KEY `order_id_idx` (`order_id`),\r\n" + 
	"  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\r\n" + 
	") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;\r\n";
	
	private static final String INSERT_ROLES = "INSERT INTO roles (role_name) values ('Admin'); "
			+ "INSERT INTO roles (role_name) values ('Manager');" + " INSERT INTO roles (role_name) values ('Client');";

	private static final String INSERT_CAR =
	"INSERT INTO `rental`.`cars`\r\n" + 
	"(`brand`,\r\n" + 
	"`model`,\r\n" + 
	"`car_type`,\r\n" + 
	"`color`,\r\n" + 
	"`price`,\r\n" + 
	"`transmission`)\r\n" + 
	"VALUES ('Audi', 'A7', 'Standard', 'blue', 100.5, 'Manual');";

	
	private static final String INSERT_USER =
	"INSERT INTO `rental`.`users`\r\n" + 
	"(`login`,\r\n" + 
	"`password`,\r\n" + 
	"`name`,\r\n" + 
	"`email`,\r\n" + 
	"`mobile`,\r\n" + 
	"`gender`,\r\n" + 
	"`role`,\r\n" + 
	"`passport`)\r\n" + 
	"VALUES ('sam', '123', 'Sam', 'qaz@ukr.net', '066451245', 'MALE', 1, 'HK561654');";
	
	public void createTables() {
		LOGGER.info("createTables method call");
		try (Statement statement = con.createStatement()) {
			statement.addBatch(CREATE_ROLES);
			statement.addBatch(CREATE_USER);
			statement.addBatch(CREATE_CARS);
			statement.addBatch(CREATE_ORDERS);
			statement.addBatch(CREATE_ACCIDENTS);
			statement.addBatch(INSERT_ROLES);
			statement.addBatch(INSERT_CAR);
			statement.addBatch(INSERT_USER);
			statement.executeBatch();

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

		}
	}

}
