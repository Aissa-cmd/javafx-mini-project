package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.RentedCar;

public class RentedCarDetailFormController {
	RentedCar carToView;
	
	@FXML
    private Label addressDisplay;

    @FXML
    private Label brandDisplay;

    @FXML
    private Label cinDisplay;

    @FXML
    private Label currentPriceDisplay;

    @FXML
    private Label emailDisplay;

    @FXML
    private Label firstNameDisplay;

    @FXML
    private Label lastNameDisplay;

    @FXML
    private Label modelDisplay;

    @FXML
    private Label phoneNumberDisplay;

    @FXML
    private Label priceAtRentTimeDisplay;

    @FXML
    private Label regestrationNumberDisplay;

    @FXML
    private Label rentDateDisplay;

    @FXML
    private Label returnDateDisplay;

    @FXML
    private Label totalPriceDisplay;
	
	public void setCarToView(RentedCar carToView) {
		this.carToView = carToView;
		
		// car details
		regestrationNumberDisplay.setText(carToView.getCar().getRegestrationNumber());
		brandDisplay.setText(carToView.getCar().getBrand());
		modelDisplay.setText(carToView.getCar().getModel());
		priceAtRentTimeDisplay.setText(String.valueOf(carToView.getCarPrice()));
		currentPriceDisplay.setText(String.valueOf(carToView.getCar().getPrice()));
		
		// client details
		firstNameDisplay.setText(carToView.getClient().getFirstName());
		lastNameDisplay.setText(carToView.getClient().getLastName());
		cinDisplay.setText(carToView.getClient().getCin());
		phoneNumberDisplay.setText(carToView.getClient().getPhoneNumber());
		emailDisplay.setText(carToView.getClient().getEmail());
		addressDisplay.setText(carToView.getClient().getAddress());
		
		// rent details
		rentDateDisplay.setText(carToView.getRentDate().toString());
		returnDateDisplay.setText(carToView.getReturnDate().toString());
		totalPriceDisplay.setText(String.valueOf(carToView.getTotalPrice()));
	}
}












