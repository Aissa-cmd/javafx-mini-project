package utiles;

import controllers.DashboardController;
import controllers.EditCarFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Car;

public class EditCarForm extends Alert {
	DashboardController dashboardcontroller;
	Car carToEdit;
	
	public EditCarForm(DashboardController dashboardcontroller, Car carToEdit) throws Exception {
		super(AlertType.CONFIRMATION);
		
		this.dashboardcontroller = dashboardcontroller;
		this.carToEdit = carToEdit;
		
		this.setHeaderText(null);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/editCarForm.fxml"));
		Parent root = loader.load();
		
		EditCarFormController editcarformcontroller = loader.getController();
		editcarformcontroller.setEditCarForm(this);
		editcarformcontroller.setDashboardController(dashboardcontroller);
		editcarformcontroller.setCarToEdit(carToEdit);
		Stage dStage = (Stage)(this.getDialogPane().getScene().getWindow());
		//Parent root = FXMLLoader.load(getClass().getResource("/resources/newCarForm.fxml"));
		dStage.setScene(new Scene(root));
		
		
	}
}
