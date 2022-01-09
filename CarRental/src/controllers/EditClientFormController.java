package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;

import database.MysqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Car;
import models.Client;
import utiles.EditCarForm;
import utiles.EditClientForm;

public class EditClientFormController {
	
	EditClientForm editclientform;
	DashboardController dashboardcontroller;
	Client clientToEdit;

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
    private Button saveBtn;

    @FXML
    void cancelBtn_Click(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void saveBtn_Click(ActionEvent event) {
    	// getting the entered data
    	String firstName = firstNameInput.getText();
    	String lastName = lastNameInput.getText();
    	String cin = cinInput.getText();
    	String phoneNumber = PhoneNumberInput.getText();
    	String email = emailInput.getText();
    	String address = addressInput.getText();
    	
    	try {
    		Connection connection = MysqlConnection.getDBConnection();
			String sql = "UPDATE `clients` SET firstName=?, lastName=?, cin=?, phoneNumber=?, email=?, address=? WHERE id=" + clientToEdit.getId();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, cin);
			ps.setString(4, phoneNumber);
			ps.setString(5, email);
			ps.setString(6, address);
			ps.execute();
			
			// close NewClientForm
			editclientform.close();
			
			// client successfully added to database
			Alert successAlert = new Alert(AlertType.INFORMATION);
			successAlert.setHeaderText(null);
			successAlert.setContentText("The client's information has benn updated successfully.");
			successAlert.showAndWait();
			
			// refresh the clients' TableView
			dashboardcontroller.getAllClients();
    	} catch (Exception e) {
    		// client could not be deleted
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("The client's information could not be updated, please try again.");
			errorAlert.showAndWait();
    	}
    }
    
    public void setEditClientForm(EditClientForm editclientform) {
    	this.editclientform = editclientform;
    }

    public void setDashboardController(DashboardController dashboardcontroller) {
    	this.dashboardcontroller = dashboardcontroller;
    }
    
    public void setClientToEdit(Client clientToEdit) {
    	this.clientToEdit = clientToEdit;
    	firstNameInput.setText(clientToEdit.getFirstName());
    	lastNameInput.setText(clientToEdit.getLastName());
    	cinInput.setText(clientToEdit.getCin());
    	PhoneNumberInput.setText(clientToEdit.getPhoneNumber());
    	emailInput.setText(clientToEdit.getEmail());
    	addressInput.setText(clientToEdit.getAddress());
    }

}
