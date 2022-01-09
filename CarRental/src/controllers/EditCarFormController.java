package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;

import database.MysqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Car;
import utiles.EditCarForm;

public class EditCarFormController {
	
	EditCarForm editcarform;
	DashboardController dashboardcontroller;
	Car carToEdit;

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
    public void initialize() {
    	
    }

    @FXML
    void cancelBtn_Click(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void saveBtn_Click(ActionEvent event) {
    	// getting the entered data
    	String regestrationNumber = regestrationNumInput.getText();
    	String brand = brandInput.getText();
    	String model = modelInput.getText();
    	int price = Integer.parseInt(priceInput.getText());
    	
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			String sql = "UPDATE `cars` SET regestrationNum=?, brand=?, model=?, price=? WHERE id=" + carToEdit.getId();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, regestrationNumber);
			ps.setString(2, brand);
			ps.setString(3, model);
			ps.setString(4, String.valueOf(price));
			ps.execute();
			
			// close NewCarForm
			editcarform.close();
			
			// car successfully added to database
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setHeaderText(null);
			successAlert.setContentText("The car's information has benn updated successfully.");
			successAlert.showAndWait();
			
			// refresh the cars' TableView
			dashboardcontroller.getAllCars();
		} catch (Exception e) {
			// car could not be deleted
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("The car's information could not be updated, please try again.");
			errorAlert.showAndWait();
		} 
    }

    public void setEditCarForm(EditCarForm editcarform) {
    	this.editcarform = editcarform;
    }

    public void setDashboardController(DashboardController dashboardcontroller) {
    	this.dashboardcontroller = dashboardcontroller;
    }
    
    public void setCarToEdit(Car carToEdit) {
    	this.carToEdit = carToEdit;
    	regestrationNumInput.setText(carToEdit.getRegestrationNumber());
    	brandInput.setText(carToEdit.getBrand());
    	modelInput.setText(carToEdit.getModel());
    	priceInput.setText(String.valueOf(carToEdit.getPrice()));
    }
}
