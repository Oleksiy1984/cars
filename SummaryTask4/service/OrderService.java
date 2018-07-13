package ua.nure.orlovskyi.SummaryTask4.service;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.dao.CarDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.OrderDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Entity;
import ua.nure.orlovskyi.SummaryTask4.model.Order;

public class OrderService {
	
	private DAOFactory dao;
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	public OrderService(DAOFactory dao) {
		this.dao = dao;
	}
	
	public Integer makeOrder(Order order) {
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		LOGGER.info("makeOrder method call");
		Integer result = null;
		try {
			CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
			dao.startTransaction();
			result = orderDAO.insertOrder(order);
			carDAO.reserveCar(order.getCar());
			dao.commit();
		} catch (SQLException e) {
			LOGGER.info(e.getMessage());
			dao.rollback();
		}
		
		return result;
	}
	
	public List<Order> getAllOrdersNotApproved(){
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		LOGGER.info("getAllOrdersNotApproved method call");
		return orderDAO.getAllOrdersNotApproved();
		
	}
	
	
	public Integer setApproved(Integer id) {
		LOGGER.info("setApproved method call");
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		return orderDAO.setApproved(id);
	}
	
	public Integer rejectOrder(Integer id, String comment) {
		LOGGER.info("rejectOrder method call");
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		CarDAO carDAO = (CarDAO) dao.getDAO(Entity.Car);
		Integer result = null;
		try {
			dao.startTransaction();
			Order order = orderDAO.findOrderById(id);
			result = orderDAO.rejectOrder(order, comment);
			carDAO.returnCar(order.getCar().getId());
			dao.commit();
		} catch (SQLException e) {
			LOGGER.error(e.toString());
			dao.rollback();
		}
		
		return result;
	}

	public List<Order> getCurrentOrders(){
		LOGGER.info("getCurrentOrders method call");
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		return orderDAO.getCurrentOrders();
	}
	
	public Order getOrderById(Integer id) {
		LOGGER.info("getOrderById method call");
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		return orderDAO.findOrderById(id);
	}
	
	public List<Order> getOrdersByClientId(Integer id) {
		LOGGER.info("getOrdersByClientId method call");
		OrderDAO orderDAO = (OrderDAO) dao.getDAO(Entity.Order);
		return orderDAO.getOrdersByClientId(id);
	}
}
