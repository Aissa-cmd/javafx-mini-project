package utiles;

import controllers.DashboardController;
import controllers.EditCarFormController;
import controllers.RentedCarDetailFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Car;
import models.RentedCar;

public class RentedCarDetails extends Alert {
	RentedCar carToView;
	
	public RentedCarDetails(RentedCar carToView) throws Exception {
		super(AlertType.CONFIRMATION);
		
		this.carToView = carToView;
		
		this.setHeaderText(null);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/rentedCarDetails.fxml"));
		Parent root = loader.load();
		
		RentedCarDetailFormController rentedcarformcontroller = loader.getController();
		rentedcarformcontroller.setCarToView(carToView);
		Stage dStage = (Stage)(this.getDialogPane().getScene().getWindow());
		//Parent root = FXMLLoader.load(getClass().getResource("/resources/newCarForm.fxml"));
		dStage.setScene(new Scene(root));
	}
}
