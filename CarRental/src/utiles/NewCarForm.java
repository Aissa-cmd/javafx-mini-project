package utiles;

import controllers.DashboardController;
import controllers.NewCarFormController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Car;

public class NewCarForm extends Alert {
	DashboardController dashboardcontroller;
	
	public NewCarForm(DashboardController dashboardcontroller) throws Exception {
		super(AlertType.CONFIRMATION);
		
		this.dashboardcontroller = dashboardcontroller;
		
		this.setHeaderText(null);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/newCarForm.fxml"));
		Parent root = loader.load();
		
		NewCarFormController newcarformcontroller = loader.getController();
		newcarformcontroller.setNewCarForm(this);
		newcarformcontroller.setDashboardController(dashboardcontroller);
		Stage dStage = (Stage)(this.getDialogPane().getScene().getWindow());
		//Parent root = FXMLLoader.load(getClass().getResource("/resources/newCarForm.fxml"));
		dStage.setScene(new Scene(root));
		
		
	}
	
}
