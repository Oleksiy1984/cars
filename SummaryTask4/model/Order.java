package ua.nure.orlovskyi.SummaryTask4.model;

import java.time.LocalDate;
import java.time.LocalDateTime;



public class Order {
	
	private Integer id;
	private String pickUpLocation;
	private String dropOffLocation;
	private LocalDate pickUpDate;
	private LocalDate dropOffDate;
	private LocalDateTime dateTimeNow;
	private User user;
	private Car car;
	private Boolean isDriver;
	private Boolean isApproved;
	private String reason;
	private Double estimatedPrice;
	private Integer numberDaysRent;
	private Boolean isRejected;
	private Double totalPrice;
	private Double damage;
	
	
	
	public Double getDamage() {
		return damage;
	}

	public void setDamage(Double damage) {
		this.damage = damage;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	public LocalDateTime getDateTimeNow() {
		;
		return dateTimeNow;
	}
	
	public LocalDate getDropOffDate() {
		return dropOffDate;
	}
	public void setDropOffDate(LocalDate dropOffDate) {
		this.dropOffDate = dropOffDate;
	}
	public LocalDate getPickUpDate() {
		
		return pickUpDate;
	}
	public void setPickUpDate(LocalDate pickUpDate) {
		this.pickUpDate = pickUpDate;
	}
	public Integer getNumberDaysRent() {
		return numberDaysRent;
	}
	public void setNumberDaysRent(Integer numberDaysRent) {
		this.numberDaysRent = numberDaysRent;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPickUpLocation() {
		return pickUpLocation;
	}
	public void setPickUpLocation(String pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}
	public String getDropOffLocation() {
		return dropOffLocation;
	}
	public void setDropOffLocation(String dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}
	
	public void setDateTimeNow(LocalDateTime dateTimeNow) {
		this.dateTimeNow = dateTimeNow;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Boolean getIsDriver() {
		return isDriver;
	}
	public void setIsDriver(Boolean isDriver) {
		this.isDriver = isDriver;
	}
	public Boolean getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Double getEstimatedPrice() {
		return estimatedPrice;
	}
	public void setEstimatedPrice(Double estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", pickUpLocation=" + pickUpLocation + ", dropOffLocation=" + dropOffLocation
				+ ", pickUpDate=" + pickUpDate + ", dropOffDate=" + dropOffDate + ", dateTimeNow=" + dateTimeNow
				+ ", user=" + user + ", car=" + car + ", isDriver=" + isDriver + ", isApproved=" + isApproved
				+ ", reason=" + reason + ", estimatedPrice=" + estimatedPrice + ", numberDaysRent=" + numberDaysRent
				+ ", isRejected=" + isRejected + ", totalPrice=" + totalPrice + ", damage=" + damage + "]";
	}


	
	
	

}
