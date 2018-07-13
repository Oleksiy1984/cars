package ua.nure.orlovskyi.SummaryTask4.service.sort;

import java.util.Comparator;

import ua.nure.orlovskyi.SummaryTask4.model.Car;

public class CarSortByPrice implements Comparator<Car> {

	@Override
	public int compare(Car car1, Car car2) {
		int byPrice = Double.compare(car1.getPrice(), car2.getPrice());
		if(byPrice != 0) {
			return byPrice;
		}
		int byId = car1.getId() - car2.getId();
		return byId;
	}



}
