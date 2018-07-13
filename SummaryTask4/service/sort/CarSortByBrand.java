package ua.nure.orlovskyi.SummaryTask4.service.sort;

import java.util.Comparator;

import ua.nure.orlovskyi.SummaryTask4.model.Car;

public class CarSortByBrand implements Comparator<Car> {


	@Override
	public int compare(Car car1, Car car2) {
		int byBrand = car1.getBrand().name().compareTo(car2.getBrand().name());
		if(byBrand != 0) {
			return byBrand;
		}
		int byId = car1.getId() - car2.getId();
		return byId;
	}

}
