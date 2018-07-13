package ua.nure.orlovskyi.SummaryTask4.service;

import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.dao.AccidentDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.CarDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.OrderDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Accident;
import ua.nure.orlovskyi.SummaryTask4.model.Entity;
import ua.nure.orlovskyi.SummaryTask4.model.Order;

public class AccidentService {
	private DAOFactory dao;
	private static final Logger LOGGER = LoggerFactory.getLogger(AccidentService.class);

	public AccidentService(DAOFactory dao) {
		this.dao = dao;
	}
	public Integer addAccident(Accident accident) {
		AccidentDAO accidentDAO = (AccidentDAO) dao.getDAO(Entity.Accident);
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		Integer result = null;
		try {
			dao.startTransaction();
			carDAO.returnCar(accident.getOrder().getCar().getId());
			result = accidentDAO.addAccident(accident);
			dao.commit();
		} catch (SQLException e) {
			LOGGER.error(e.toString());
			dao.rollback();
		}
		return result;
	}
}
