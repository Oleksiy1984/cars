package ua.nure.orlovskyi.SummaryTask4.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.action.AddAccidentAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.AddCarAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.AddCarFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.AddManagerAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.AddManagerFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.BookingHistoryAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.CarSortSelectionAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ChangeLocaleAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.GETUpdateCarFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.IndexAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.InvalidateSessionAndRemoveCookiesAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.LoginAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.LoginFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.LogoutAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ManagerCarReturnAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ManagerProcessOrderAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ManagerRejectOrderAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ManagerRejectOrderFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ManagerReturnCarAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.OrderAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ProcessCarsAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.RegisterAccidentAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.RegisterAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.RegisterFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ReserveAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ReserveFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.SearchAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.SearchFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.UpdateCarAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.UpdateCarFormAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.UploadAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.UsersAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.UsersProcessAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ViewAllCarsAction;
import ua.nure.orlovskyi.SummaryTask4.controller.action.ViewOrdersAction;

public class ActionFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionFactory.class);
    private Map<String, Action> actions = new HashMap<>();
    private Action action;

    /**
     * This constructor initializes keys and Action class instances of them.
     */
   public ActionFactory() {            
    	actions.put("GET/frontController/register", new RegisterFormAction());
    	actions.put("POST/frontController/register", new RegisterAction());
        actions.put("POST/frontController/login", new LoginAction()); 
        actions.put("GET/frontController/search", new SearchFormAction());
        actions.put("POST/frontController/search", new SearchAction());
        actions.put("GET/frontController/index", new IndexAction());
        actions.put("POST/frontController/index", new CarSortSelectionAction());
        actions.put("GET/frontController/login", new LoginFormAction());
        actions.put("POST/frontController/reserveform", new ReserveAction());
        actions.put("GET/frontController/reserveform", new ReserveFormAction());
        actions.put("POST/frontController/reserve", new OrderAction());
        actions.put("GET/frontController/logout", new LogoutAction());
        actions.put("POST/frontController/processorder", new ManagerProcessOrderAction());
        actions.put("POST/frontController/rejectorderform", new ManagerRejectOrderFormAction());
        actions.put("POST/frontController/rejectorder", new ManagerRejectOrderAction());
        actions.put("GET/frontController/returncarform", new ManagerCarReturnAction());
        actions.put("POST/frontController/returncar", new ManagerReturnCarAction());
        actions.put("GET/frontController/cars", new ViewAllCarsAction());
        actions.put("POST/frontController/cars", new ProcessCarsAction());
        actions.put("GET/frontController/accidentform", new AddAccidentAction());
        actions.put("POST/frontController/registeraccident", new RegisterAccidentAction());
        actions.put("GET/frontController/invalidateSessionAndRemoveCookies", new InvalidateSessionAndRemoveCookiesAction());
        actions.put("GET/frontController/users", new UsersAction());
        actions.put("POST/frontController/users", new UsersProcessAction());
        actions.put("POST/frontController/addmanager", new AddManagerAction());
        actions.put("GET/frontController/addmanager", new AddManagerFormAction());
        actions.put("GET/frontController/addcar", new AddCarFormAction());
        actions.put("POST/frontController/addcar", new AddCarAction());
        actions.put("POST/frontController/updatecarform", new UpdateCarFormAction());
        actions.put("GET/frontController/updatecarform", new GETUpdateCarFormAction());
        actions.put("POST/frontController/updatecar", new UpdateCarAction());
        actions.put("GET/frontController/history", new BookingHistoryAction());
        actions.put("GET/frontController/locale", new ChangeLocaleAction());
        actions.put("GET/frontController/orders", new ViewOrdersAction());
        actions.put("POST/frontController/upload", new UploadAction());
        LOGGER.info("Action container was successfully initialized");
        LOGGER.info("Number of actions --> " + actions.size());
    }

   /**
    * This method constructs key, that helps to get necessary class.
    */
    public Action getAction(HttpServletRequest request) {
    	
    	String path = request.getServletPath() + request.getPathInfo();     
        String actionKey = request.getMethod() + path;
        LOGGER.info(actionKey);
        action = actions.get(actionKey);
        return action;  
    }
}
