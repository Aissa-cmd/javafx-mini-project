package utiles;

import controllers.DashboardController;
import controllers.EditCarFormController;
import controllers.EditClientFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Car;
import models.Client;

public class EditClientForm extends Alert {
	DashboardController dashboardcontroller;
	Client clientToEdit;
	
	public EditClientForm(DashboardController dashboardcontroller, Client clientToEdit) throws Exception {
		super(AlertType.CONFIRMATION);
		
		this.dashboardcontroller = dashboardcontroller;
		this.clientToEdit = clientToEdit;
		
		this.setHeaderText(null);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/editClientForm.fxml"));
		Parent root = loader.load();
		
		EditClientFormController editclientformcontroller = loader.getController();
		editclientformcontroller.setEditClientForm(this);
		editclientformcontroller.setDashboardController(dashboardcontroller);
		editclientformcontroller.setClientToEdit(clientToEdit);
		Stage dStage = (Stage)(this.getDialogPane().getScene().getWindow());
		//Parent root = FXMLLoader.load(getClass().getResource("/resources/newCarForm.fxml"));
		dStage.setScene(new Scene(root));
		
		
	}
}
