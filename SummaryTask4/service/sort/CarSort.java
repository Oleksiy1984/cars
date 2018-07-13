package ua.nure.orlovskyi.SummaryTask4.service.sort;

import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import ua.nure.orlovskyi.SummaryTask4.model.Car;

public final class CarSort {
	
	public static List<Car> sortByPrice(List<Car> carList) {
        Collections.sort(carList, new CarSortByPrice());
        return carList;
    }

    public static List<Car> sortByBrand(List<Car> carList) {
        Collections.sort(carList, new CarSortByBrand());
        return carList;
    }

}
