package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.scene.Parent;

public class DBConnection {
	
	public static void changeScene(ActionEvent enent, String fxmlfile, String title) {
		Parent root = null;
		Connection connection = null;
		PreparedStatement getuses = null;
		ResultSet allusers = null;
		try {
			connection = DriverManager.getConnection(title);
		} catch(Exception e) {
			
		}
	}
	
}
