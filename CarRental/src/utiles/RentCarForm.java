package utiles;

import controllers.DashboardController;
import controllers.EditCarFormController;
import controllers.RentCarFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Car;

public class RentCarForm extends Alert {
	DashboardController dashboardcontroller;
	Car carToRent;
	
	public RentCarForm(DashboardController dashboardcontroller, Car carToRent) throws Exception {
		super(AlertType.CONFIRMATION);
		
		this.dashboardcontroller = dashboardcontroller;
		this.carToRent = carToRent;
		
		this.setHeaderText(null);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/rentCarForm.fxml"));
		Parent root = loader.load();
		
		RentCarFormController rentcarformcontroller = loader.getController();
		rentcarformcontroller.setRentCarForm(this);
		rentcarformcontroller.setDashboardController(dashboardcontroller);
		rentcarformcontroller.setCarToRent(carToRent);
		Stage dStage = (Stage)(this.getDialogPane().getScene().getWindow());
		//Parent root = FXMLLoader.load(getClass().getResource("/resources/newCarForm.fxml"));
		dStage.setScene(new Scene(root));
		
		
	}
}
