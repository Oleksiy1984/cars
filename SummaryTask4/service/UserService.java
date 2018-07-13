package ua.nure.orlovskyi.SummaryTask4.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.dao.UserDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Entity;
import ua.nure.orlovskyi.SummaryTask4.model.User;

public class UserService {
	private DAOFactory dao;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public UserService(DAOFactory dao) {
		this.dao = dao;
	}
	
	public List<User> getAllUsers() {
		LOGGER.info("getAllUsers method call");
		UserDAO userDAO = (UserDAO) dao.getDAO(Entity.User);
		return userDAO.getAllUsers();
	}
	
	public Integer blockUser(Integer id) {
		LOGGER.info("blockUser method call");
		UserDAO userDAO = (UserDAO) dao.getDAO(Entity.User);
		return userDAO.blockUser(id);
	}
	
	public Integer unBlockUser(Integer id) {
		LOGGER.info("unBlockUser method call");
		UserDAO userDAO = (UserDAO) dao.getDAO(Entity.User);
		return userDAO.unBlockUser(id);
	}
	
	public Integer addManager(User user) {
		LOGGER.info("addManager method call");
		PasswordService handler = new PasswordService();
		String code = handler.getHashedPassword(user.getPassword());
		user.setPassword(code);
		UserDAO userDAO = (UserDAO) dao.getDAO(Entity.User);
		String passport = null;
		String login = null;
		passport = userDAO.getPassport(user.getPassport());
		login = userDAO.getLogin(user.getLogin());
		if (passport == null && login == null) {
			return userDAO.insertManager(user);
		}
		return 0;
	}
}
