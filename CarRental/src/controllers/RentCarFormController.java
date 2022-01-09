package controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import database.MysqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Car;
import models.Client;
import utiles.DateMath;
import utiles.EditClientForm;
import utiles.RentCarForm;

public class RentCarFormController {
	
	RentCarForm rentcarform;
	DashboardController dashboardcontroller;
	Car carToRent;

    @FXML
    private TextField PhoneNumberInput;

    @FXML
    private TextArea addressInput;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField cinInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private DatePicker rentDateInput;

    @FXML
    private DatePicker returnDateInput;

    @FXML
    private Button saveBtn;
    
    @FXML
    private Label totalPriceDisplay;

    @FXML
    private Label carPriceDisplay;
    
    @FXML
    public void initialize() {
    	rentDateInput.setDayCellFactory(d -> 
    		new DateCell() {
    			@Override
    		    public void updateItem(LocalDate item, boolean empty) {
    		        super.updateItem(item, empty);
    		        if (item.isBefore(LocalDate.now())) { //Disable all dates after required date
    		            setDisable(true);
    		            //setStyle("-fx-background-color: #c30;"); //To set background on different color
    		        }
    		    }
    		}
		);
    }
    
    @FXML
    void cancelBtn_Click(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void saveBtn_Click(ActionEvent event) {
    	
    	
    	// get the entered data
    	String firstname = firstNameInput.getText();
    	String lastname = lastNameInput.getText();
    	String cin = cinInput.getText();
    	String phoneNumber = PhoneNumberInput.getText();
    	String email = emailInput.getText();
    	String address = addressInput.getText();
    	
    	LocalDate rentDate = rentDateInput.getValue();
    	LocalDate returnDate = returnDateInput.getValue();
    	int totalPrice = Integer.parseInt(totalPriceDisplay.getText());
    	
    	try {
    		Connection connection = MysqlConnection.getDBConnection();
    		
    		// create a new client
			String sql = "INSERT INTO clients(firstName, lastName, cin, phoneNumber, email, address) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, cin);
			ps.setString(4, phoneNumber);
			ps.setString(5, email);
			ps.setString(6, address);
			ps.execute();
			
			// getting the client id from database
			PreparedStatement ps1 = connection.prepareStatement("SELECT id FROM clients WHERE cin=? limit 1");
			ps1.setString(1, cin);
			ResultSet result = ps1.executeQuery();
			int clientId = 1;
			while(result.next()) {
				clientId = result.getInt("id");
			}
			
			// create a new record in `rented_cars` table
			String sql1 = "INSERT INTO rented_cars(client, car, price, totoalPrice, rent_date, return_date) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps2 = connection.prepareStatement(sql1);
			ps2.setString(1, String.valueOf(clientId));
			ps2.setString(2, String.valueOf(carToRent.getId()));
			ps2.setString(3, String.valueOf(carToRent.getPrice()));
			ps2.setString(4, String.valueOf(totalPrice));
			ps2.setString(5, rentDate.toString());
			ps2.setString(6, returnDate.toString());
			ps2.execute();
			
			// update car: set available to false
			PreparedStatement ps3 = connection.prepareStatement("UPDATE cars SET status=false WHERE id="+carToRent.getId());
			ps3.execute();
			
			// close rent car form
			rentcarform.close();
			
			// show success message
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setHeaderText(null);
			successAlert.setContentText("The car has benn rented successfully.");
			successAlert.showAndWait();
	    	// refresh the clients' TableView
			dashboardcontroller.getAllClients();
	    	// refresh the cars' TableView
			dashboardcontroller.getAllCars();
			// refresh the rentedCars' TableView
			dashboardcontroller.getAllRentedCars();
    	}
    	// show error message
    	catch (Exception e) {
    		Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("There was an error, please try again.");
			errorAlert.showAndWait();
    	}
    	
    	
    	
    	
    }
    
    @FXML
    void rentDateInput_Change(ActionEvent event) {
    	returnDateInput.setDisable(false);
    	
    	LocalDate minDate = rentDateInput.getValue().plusDays(1);
    	
    	returnDateInput.setDayCellFactory(d -> 
			new DateCell() {
				@Override
			    public void updateItem(LocalDate item, boolean empty) {
			        super.updateItem(item, empty);
			        if (item.isBefore(minDate)) { //Disable all dates after required date
			            setDisable(true);
			            //setStyle("-fx-background-color: #c30;"); //To set background on different color
			        }
			    }
			}
		);
    	
    	try {
    		if(returnDateInput.getValue().isBefore(minDate)) {
        		returnDateInput.setValue(minDate);
        	}
    		
    		setTotalPrice();
    	} catch (Exception e) {
    		// to prevent the error when the returnDateInput is first set and has no value selected
    	}
    	
    }
    
    @FXML
    void returnDateInput_Change(ActionEvent event) {
    	setTotalPrice();
    }
    
    @FXML
    void cinInput_TextChange(KeyEvent event) {
    	// getting the entered cin
    	String enteredCin = ((TextField)event.getTarget()).getText();
    	// check
    	Object[] matchedClients = dashboardcontroller.clientData.filtered(cl -> cl.getCin().compareToIgnoreCase(enteredCin) == 0).toArray();
    	if(matchedClients.length > 0) {
    		// there is already a client with that cin
    		System.out.println("client already exist");
    	}
    }
    
    public void setTotalPrice() {
    	LocalDate rentDate = rentDateInput.getValue();
    	LocalDate returnDate = returnDateInput.getValue();
    	
    	long numOfDays = DateMath.getNumberOfDays(Date.valueOf(rentDate), Date.valueOf(returnDate));
    	
    	long totalPrice = numOfDays * carToRent.getPrice();
    	
    	totalPriceDisplay.setText(String.valueOf(totalPrice));
    }

    public void setRentCarForm(RentCarForm rentcarform) {
    	this.rentcarform = rentcarform;
    }

    public void setDashboardController(DashboardController dashboardcontroller) {
    	this.dashboardcontroller = dashboardcontroller;
    }
    
    public void setCarToRent(Car carToRent) {
    	this.carToRent = carToRent;
    	carPriceDisplay.setText(String.valueOf(carToRent.getPrice()));
    }
}
