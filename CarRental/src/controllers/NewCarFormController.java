package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;

import database.MysqlConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Car;
import utiles.NewCarForm;

public class NewCarFormController {

	NewCarForm newcarform;
	DashboardController dashboardcontroller;
	
    @FXML
    private TextField brandInput;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField modelInput;

    @FXML
    private TextField priceInput;

    @FXML
    private TextField regestrationNumInput;

    @FXML
    private Button saveBtn;

    @FXML
    void cancelBtn_Click(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void saveBtn_Click(ActionEvent event) {
    	System.out.println("save btn click");
    	
    	// getting the entered data
    	String regestrationNumber = regestrationNumInput.getText();
    	String brand = brandInput.getText();
    	String model = modelInput.getText();
    	int price = Integer.parseInt(priceInput.getText());
    	
    	System.out.println("the entered data: ");
    	System.out.printf("Brand = %s \n", brand);
    	System.out.printf("model = %s \n", model);
    	System.out.printf("price = %d \n", price);
    	
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			String sql = "INSERT INTO `cars`(regestrationNum, brand, model, status, price) VALUES(?, ?, ?, true, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, regestrationNumber);
			ps.setString(2, brand);
			ps.setString(3, model);
			ps.setString(4, String.valueOf(price));
			ps.execute();
			
			// close NewCarForm
			newcarform.close();
			
			// car successfully added to database
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setHeaderText(null);
			successAlert.setContentText("The car has been created successfully.");
			successAlert.showAndWait();
			
			// refresh the cars' TableView
			dashboardcontroller.getAllCars();
		} catch (Exception e) {
			// car could not be deleted
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("The car could not be created, please try again.");
			errorAlert.showAndWait();
		} 
    }
    
    public void setNewCarForm(NewCarForm newcarform) {
    	this.newcarform = newcarform;
    }

    public void setDashboardController(DashboardController dashboardcontroller) {
    	this.dashboardcontroller = dashboardcontroller;
    }
}
