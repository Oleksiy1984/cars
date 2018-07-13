package ua.nure.orlovskyi.SummaryTask4.model;


public class Car {
	private Integer id;
	private Brand brand;
	private String model;
	private CarType carType;
	private String color;
	private Double price;
	private Boolean isAvailable;
	private String photo;
	private Transmission transmission;
	
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Transmission getTransmission() {
		return transmission;
	}
	public void setTransmission(Transmission transmission) {
		this.transmission = transmission;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public CarType getCarType() {
		return carType;
	}
	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (brand != other.brand)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", model=" + model + ", carType=" + carType + ", color=" + color
				+ ", price=" + price + ", isAvailable=" + isAvailable + ", photo=" + photo + ", transmission="
				+ transmission + "]";
	}

	
	
	

}
