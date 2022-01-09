package models;

import java.time.LocalDate;

public class RentedCar {
	int id;
	Client client;
	Car car;
	int carPrice;
	int totalPrice;
	LocalDate rentDate;
	LocalDate returnDate;
	String clientFullName;
	String carName;
	
	public RentedCar(int id, Client client, Car car, int carPrice, int totalPrice, LocalDate rentDate,
			LocalDate returnDate) {
		super();
		this.id = id;
		this.client = client;
		this.car = car;
		this.carPrice = carPrice;
		this.totalPrice = totalPrice;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.clientFullName = client.getFullName();
		this.carName = car.getCarName();
	}

	public RentedCar(Client client, Car car, int carPrice, int totalPrice, LocalDate rentDate, LocalDate returnDate) {
		super();
		this.client = client;
		this.car = car;
		this.carPrice = carPrice;
		this.totalPrice = totalPrice;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public int getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(int carPrice) {
		this.carPrice = carPrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDate getRentDate() {
		return rentDate;
	}

	public void setRentDate(LocalDate rentDate) {
		this.rentDate = rentDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public String getClientFullName() {
		return clientFullName;
	}

	public void setClientFullName(String clientFullName) {
		this.clientFullName = clientFullName;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}
	
	public String getClientCin() {
		return client.getCin();
	}
	
	public String getClientPhoneNumber() {
		return client.getPhoneNumber();
	}
	
}
