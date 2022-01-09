package models;

public class Car {
	int id;
	String regestrationNumber;
	String brand;
	String model;
	boolean status;
	int price;
	
	public Car(int id, String regestrationNumber, String brand, String model, boolean status, int price) {
		this.id = id;
		this.regestrationNumber = regestrationNumber;
		this.brand = brand;
		this.model = model;
		this.status = status;
		this.price = price;
	}
	
	public Car(String regestrationNumber, String brand, String model, boolean status, int price) {
		this.regestrationNumber = regestrationNumber;
		this.brand = brand;
		this.model = model;
		this.status = status;
		this.price = price;
	}
	
	public String getCarName() {
		return this.brand + " || " + this.model;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRegestrationNumber() {
		return regestrationNumber;
	}
	
	public void setRegestrationNumber(String regestrationNumber) {
		this.regestrationNumber = regestrationNumber;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return String.format("Car[id=%d, regestrationNumber=%s, brand=%s, model=%s, status=%s, price=%d]",
				id, regestrationNumber, brand, model, status, price
				);
	}
}

