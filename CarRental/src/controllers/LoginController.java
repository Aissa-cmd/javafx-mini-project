package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MysqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
    private TextField passwordInput;

    @FXML
    private TextField usernameInput;
    
    @FXML
    private Label errorMessage;

    @FXML
    void loginBtn_Click(ActionEvent event) throws Exception {
    	String username = usernameInput.getText();
    	String password = passwordInput.getText();
    	
    	try {
    		Connection connection = MysqlConnection.getDBConnection();
    		PreparedStatement psCheckUserExist = connection.prepareStatement("SELECT * FROM `users` WHERE username = ? AND password = ?");
    		psCheckUserExist.setString(1, username);
    		psCheckUserExist.setString(2, password);
    		ResultSet results = psCheckUserExist.executeQuery();
    		
    		if(results.isBeforeFirst()) {
    			// user exist
    			
    			// show the dashboard interface
    			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        		Parent root = FXMLLoader.load(getClass().getResource("/resources/dashboardInterface.fxml"));
        		stage.setScene(new Scene(root));
        		stage.setResizable(true);
        		stage.setTitle("Dashboard");
    		} else {
    			// user does not exist
    			errorMessage.setVisible(true);
    		}
    		
    	} catch (Exception e) {
    		// alert 'service not available right now'
    		System.out.println("service not available right now");
    	}
    	
    	
//    	if(password.compareTo("admin") == 0 && username.compareTo("admin") == 0) {
//    		System.out.println(username + ":" + password);
//    		
//    		// change scene to the dashboardInterface.fxml
//    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//    		Parent root = FXMLLoader.load(getClass().getResource("/resources/dashboardInterface.fxml"));
//    		stage.setScene(new Scene(root));
//    		stage.setResizable(true);
//    		stage.setTitle("Dashboard");
//    		// ===========================================
//    	} else {
//    		errorMessage.setVisible(true);
//    	}
    }
}
