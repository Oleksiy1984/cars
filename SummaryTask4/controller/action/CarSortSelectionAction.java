package ua.nure.orlovskyi.SummaryTask4.controller.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.service.sort.CarSort;

public class CarSortSelectionAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(CarSortSelectionAction.class);

	/**
	 * This method provides ability to sort or get selection of current cars.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Car sort action");
		HttpSession session = request.getSession();
		String price = request.getParameter("sortPrice");
		String sortBrand = request.getParameter("sortBrand");
		List<Car> cars = (List<Car>) session.getAttribute("cars");

		if (price != null){
		    session.setAttribute("cars", CarSort.sortByPrice(cars));
		    session.setAttribute("sort", "price");
		} else if (sortBrand != null){
			session.setAttribute("cars", CarSort.sortByBrand(cars));
			session.setAttribute("sort", "brand");
		}
		
		String carType = request.getParameter("carType");
		String brand = request.getParameter("brand");
		List<Car> allCars = (List<Car>) session.getAttribute("allCars");
		if (request.getParameter("selectCarType") != null){
			
			Iterator<Car> iterator = allCars.iterator();
			List<Car> selectionList = new ArrayList<>();
			while (iterator.hasNext()) {
			    Car car = iterator.next();
			    if (car.getCarType().name().equals(carType)) {
			    	selectionList.add(car);
			    }
			}
		    session.setAttribute("cars", selectionList);
		    session.setAttribute("selection", carType);
		    session.setAttribute("selectedCarType", carType);
		    session.removeAttribute("sort");
		} else if (request.getParameter("selectBrand") != null){
			Iterator<Car> iterator = allCars.iterator();
			List<Car> selectionList = new ArrayList<>();
			while (iterator.hasNext()) {
			    Car car = iterator.next();
			    if (car.getBrand().name().equals(brand)) {
			    	selectionList.add(car);
			    }
			}
			session.setAttribute("cars", selectionList);
			session.setAttribute("selection", brand);
			session.setAttribute("selectedCarBrand", brand);
			session.removeAttribute("sort");
		}
		else if(request.getParameter("closeSelection")!=null) {
			session.removeAttribute("sort");
			 session.setAttribute("cars", allCars);
			 session.removeAttribute("selection");
		}

		return "index";
	}

}
