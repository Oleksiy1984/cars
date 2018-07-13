package ua.nure.orlovskyi.SummaryTask4.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.dao.CarDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.OrderDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.model.Entity;

public class CarService {

	private DAOFactory dao;
	private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

	public CarService(DAOFactory dao) {
		this.dao = dao;
	}

	public List<Car> getAllAvailableCars() {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.getAllAvailableCars();
	}

	public List<Car> getAllCars() {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.getAllCars();
	}

	public Car getCarById(Integer id) {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.getCarById(id);
	}

	public List<Car> getAllAvailableCarsAtDate(LocalDate pickUpDate, LocalDate dropOff) {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		List<Car> carList = carDAO.getAllAvailableCarsAtDate(pickUpDate, dropOff);
		List<Integer> list = carDAO.getCarsInDesiredTimeFrame(pickUpDate, dropOff);
		if (list != null) {
			List<Car> repeatCar = new ArrayList<>();
			for (Integer id : list) {
				repeatCar.add(carDAO.getCarById(id));
			}
			carList.removeAll(repeatCar);
		}
		return carList;

	}

	public Integer reserveCar(Car car) {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.reserveCar(car);
	}
	
	public Integer updateCarPhoto(Car car) {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.updateCarPhoto(car);
	}

	public List<Car> getAllUnAvailableCars() {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.getAllUnAvailableCars();
	}

	public Integer returnCar(Integer id) {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.returnCar(id);
	}

	public Integer addCar(Car car) {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.insertCar(car);
	}
	
	public Integer updateCar(Car car) {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		return carDAO.updateCar(car);
	}

	public Integer removeCar(Integer id) {
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		Integer result = null;
		try {
			dao.startTransaction();
			orderDAO.deleteOrderByCarId(id);
			result = carDAO.deleteCar(id);
			dao.commit();
		}
		catch (SQLException e) {
			LOGGER.error(e.toString());
			dao.rollback();
		}
		return result;
	}
}
