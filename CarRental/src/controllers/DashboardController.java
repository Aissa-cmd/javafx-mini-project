package controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

import database.MysqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Car;
import models.Client;
import models.RentedCar;
import utiles.EditCarForm;
import utiles.EditClientForm;
import utiles.NewCarForm;
import utiles.RentCarForm;
import utiles.RentedCarDetails;

public class DashboardController {
    
	@FXML
    private Button logoutBtn;
	
	@FXML
    private TextField searchClientsInput;
	
	@FXML
    private TextField searchCarsInput;
	
	@FXML
    private TextField searchRentedCarsInput;

	@FXML
    private TableColumn<Car, String> brandCol;

    @FXML
    private TableView<Car> carTableView;

    @FXML
    private TableColumn<Car, Integer> idCol;

    @FXML
    private TableColumn<Car, String> modelCol;

    @FXML
    private TableColumn<Car, Integer> priceCol;

    @FXML
    private TableColumn<Car, String> regestrationNumberCol;

    @FXML
    private TableColumn<Car, Boolean> statusCol;
    
    @FXML
    private TableView<Client> clientTableView;
    
    @FXML
    private TableColumn<Client, Integer> clientIdCol;
    
    @FXML
    private TableColumn<Client, String> firstNameCol;
    
    @FXML
    private TableColumn<Client, String> lastNameCol;
    
    @FXML
    private TableColumn<Client, String> cinCol;
    
    @FXML
    private TableColumn<Client, String> phoneNumberCol;
    
    @FXML
    private TableColumn<Client, String> emailCol;
    
    @FXML
    private TableColumn<Client, String> addressCol;
    
    @FXML
    private TableColumn<RentedCar, LocalDate> rentDateCol;

    @FXML
    private TableColumn<RentedCar, String> rentedCarClientCol;

    @FXML
    private TableColumn<RentedCar, Integer> rentedCarIdCol;

    @FXML
    private TableColumn<RentedCar, String> rentedCarNameCol;

    @FXML
    private TableColumn<RentedCar, Integer> rentedCarPriceCol;

    @FXML
    private TableView<RentedCar> rentedCarsTableView;
    
    @FXML
    private TableColumn<RentedCar, Integer> totalPriceCol;

    @FXML
    private TableColumn<RentedCar, LocalDate> returnDateCol;
    
    @FXML
    private TableView<RentedCar> carRentHistoryTableView;
    
    @FXML
    private TableColumn<RentedCar, Integer> historyCarPriceCol;

    @FXML
    private TableColumn<RentedCar, String> historyClCinCol;

    @FXML
    private TableColumn<RentedCar, String> historyClFullnameCol;

    @FXML
    private TableColumn<RentedCar, String> historyClPhoneNumberCol;

    @FXML
    private TableColumn<RentedCar, String> historyRentDateCol;

    @FXML
    private TableColumn<RentedCar, String> historyReturnDateCol;

    @FXML
    private TableColumn<RentedCar, Integer> historyTotalPriceCol;
    
    @FXML
    private TableView<RentedCar> clientRentHistoryTableview;
    
    @FXML
    private TableColumn<RentedCar, String> clHistoryCarNameCol;

    @FXML
    private TableColumn<RentedCar, Integer> clHistoryCarPriceCol;

    @FXML
    private TableColumn<RentedCar, String> clHistoryRentDateCol;

    @FXML
    private TableColumn<RentedCar, String> clHistoryReturnDateCol;

    @FXML
    private TableColumn<RentedCar, Integer> clHistoryTotalPriceCol;
    
    @FXML
    private Label rhBrand;

    @FXML
    private Label rhModel;

    @FXML
    private Label rhPrice;

    @FXML
    private Label rhRegestrationNumber;

    @FXML
    private Label rhStatus;
    
    @FXML
    private Label clHistoryClientAddress;

    @FXML
    private Label clHistoryClientCin;

    @FXML
    private Label clHistoryClientEmail;

    @FXML
    private Label clHistoryClientFirstName;

    @FXML
    private Label clHistoryClientLastName;

    @FXML
    private Label clHistoryClientPhoneNumber;
    
    @FXML
    private Tab carRentHistoryTab;
    
    @FXML
    private Tab clientRentHistoryTab;
    
    @FXML
    private TabPane mainTabPane;
    
    ObservableList<Car> carData;
    ObservableList<Client> clientData;
    ObservableList<RentedCar> rentedCarsData;
    ObservableList<RentedCar> carRentHistoryData = FXCollections.observableArrayList();
    ObservableList<RentedCar> clientRentHistoryData = FXCollections.observableArrayList();
    
    @FXML
    void logoutBtn_Click(ActionEvent event) throws Exception {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/resources/loginInterface.fxml"));
    	stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.setWidth(400);
		stage.setHeight(500);
		stage.setTitle("Rent a Car");
    }

    @FXML
    public void initialize() {
    	// close `Rent History` tab
    	carRentHistoryTab.getTabPane().getTabs().remove(carRentHistoryTab);
    	clientRentHistoryTab.getTabPane().getTabs().remove(clientRentHistoryTab);
    	
    	carData = FXCollections.observableArrayList();
    	clientData = FXCollections.observableArrayList();
    	rentedCarsData = FXCollections.observableArrayList();
    	
    	idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    	regestrationNumberCol.setCellValueFactory(new PropertyValueFactory<>("regestrationNumber"));
    	brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
    	modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
    	statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    	priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    	
    	clientIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    	firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    	lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    	cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
    	phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    	emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    	
    	rentedCarIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    	rentedCarNameCol.setCellValueFactory(new PropertyValueFactory<>("carName"));
    	rentedCarClientCol.setCellValueFactory(new PropertyValueFactory<>("clientFullName"));
    	rentedCarPriceCol.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
    	rentDateCol.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
    	returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    	totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    	
    	historyClFullnameCol.setCellValueFactory(new PropertyValueFactory<>("clientFullName"));
    	historyClCinCol.setCellValueFactory(new PropertyValueFactory<>("clientCin"));
    	historyClPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("clientPhoneNumber"));
    	historyCarPriceCol.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
    	historyTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    	historyRentDateCol.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
    	historyReturnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    	
    	clHistoryCarNameCol.setCellValueFactory(new PropertyValueFactory<>("carName"));
    	clHistoryCarPriceCol.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
    	clHistoryTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    	clHistoryRentDateCol.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
    	clHistoryReturnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    	
    	carTableView.setItems(carData);
    	clientTableView.setItems(clientData);
    	carRentHistoryTableView.setItems(carRentHistoryData);
    	clientRentHistoryTableview.setItems(clientRentHistoryData);
    	
    	getAllCars();
    	getAllClients();
    	getAllRentedCars();
    	
    	FilteredList<Car> filteredCarData = new FilteredList<>(carData, b -> true);
    	FilteredList<Client> filteredClientData = new FilteredList<>(clientData, b -> true);
    	FilteredList<RentedCar> filteredRentedCarData = new FilteredList<>(rentedCarsData, b -> true);
        
        searchClientsInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	filteredClientData.setPredicate(client -> {
        		if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
        			return true;
        		}
        		
        		String searchKeyword = newValue.toLowerCase();
        		
        		if(client.getFirstName().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(client.getLastName().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(client.getCin().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(client.getPhoneNumber().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(client.getEmail().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(client.getAddress().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else {
        			return false;
        		}
        	});
        });
        
        searchCarsInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	filteredCarData.setPredicate(car -> {
        		if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
        			return true;
        		}
        		
        		String searchKeyword = newValue.toLowerCase();
        		
        		if(car.getRegestrationNumber().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(car.getBrand().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(car.getModel().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(String.valueOf(car.getPrice()).indexOf(searchKeyword) > -1) {
        			return true;
        		} else {
        			return false;
        		}
        	});
        });
        
        searchRentedCarsInput.textProperty().addListener((observable, oldValue, newValue) -> {
        	filteredRentedCarData.setPredicate(rentedCar -> {
        		if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
        			return true;
        		}
        		
        		String searchKeyword = newValue.toLowerCase();
        		
        		if(rentedCar.getCarName().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else if(rentedCar.getClient().getFullName().toLowerCase().indexOf(searchKeyword) > -1) {
        			return true;
        		} else {
        			return false;
        		}
        	});
        });

        SortedList<Car> sortedCarData = new SortedList<>(filteredCarData);
        sortedCarData.comparatorProperty().bind(carTableView.comparatorProperty());
        
        SortedList<Client> sortedClientData = new SortedList<>(filteredClientData);
        sortedClientData.comparatorProperty().bind(clientTableView.comparatorProperty());
        
        SortedList<RentedCar> sortedRentedCarData = new SortedList<>(filteredRentedCarData);
        sortedRentedCarData.comparatorProperty().bind(rentedCarsTableView.comparatorProperty());
        
        carTableView.setItems(sortedCarData);
        clientTableView.setItems(sortedClientData);
    	rentedCarsTableView.setItems(sortedRentedCarData);
    }
    
    public void getAllCars() {
    	carData.clear();
    	
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM `cars`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id = results.getInt("id");
				String regestrationNumber = results.getString("regestrationNum");
				String brand = results.getString("brand");
				String model = results.getString("model");
				boolean status = results.getBoolean("status");
				int price = results.getInt("price");
				carData.add(new Car(id, regestrationNumber, brand, model, status, price));
				
			}
		} catch (Exception e) {
			// alert 'service not available right now'
    		System.out.println("service not available right now");
		}
    	
    }
    
    public void getAllClients() {
    	clientData.clear();
    	
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM `clients`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id = results.getInt("id");
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				String cin = results.getString("cin");
				String phoneNumber = results.getString("phoneNumber");
				String email = results.getString("email");
				String address = results.getString("address");
				clientData.add(new Client(id, firstName, lastName, cin, phoneNumber, email, address));
				
			}
		} catch (Exception e) {
			// alert 'service not available right now'
    		System.out.println("service not available right now");
		}
    }
    
    public void getAllRentedCars() {
    	rentedCarsData.clear();

    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql = "SELECT * FROM rented_cars";
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id = results.getInt("id");
				int clientId = results.getInt("client");
				int carId = results.getInt("car");
				int price = results.getInt("price");
				int totalPrice = results.getInt("totoalPrice");
				Date rentDate = results.getDate("rent_date");
				Date returnDate = results.getDate("return_date");
				// get car from carDate
				Car car = (Car)carData.filtered(cr -> cr.getId() == carId).toArray()[0];
				// get client from clientDate	
				Client client = (Client)clientData.filtered(cl -> cl.getId() == clientId).toArray()[0];
				
				rentedCarsData.add(new RentedCar(id, client, car, price, totalPrice, rentDate.toLocalDate(), returnDate.toLocalDate()));
			}
		} catch (Exception e) {
			// alert 'service not available right now'
    		System.out.println("service not available right now");
		}
    }
    
    @FXML
    void newCarBtn_Click(ActionEvent event) throws Exception {
    	NewCarForm newCarForm = new NewCarForm(this);
    	newCarForm.showAndWait();
    }

    @FXML
    void editCarBtn_Click(ActionEvent event) throws Exception {  	
    	int index = carTableView.getSelectionModel().getSelectedIndex();
    	if(index >= 0) {
    		Car carToEdit = carTableView.getSelectionModel().getSelectedItem();
    		EditCarForm editCarForm = new EditCarForm(this, carToEdit);
    		editCarForm.showAndWait();
    	}
    }
    
    @FXML
    void deleteCarBtn_Click(ActionEvent event) {
    	int index = carTableView.getSelectionModel().getSelectedIndex();
    	if(index >= 0) {
    		Car carToDelete = carTableView.getSelectionModel().getSelectedItem();
    		
    		// show confirmation alert
    		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
    		confirmationAlert.setHeaderText("Confirm that you want to delete this car.");
    		confirmationAlert.setContentText("Regestration Number : " + carToDelete.getRegestrationNumber() + 
    			"\nBrand : " + carToDelete.getBrand() +
    			"\nModel : " + carToDelete.getModel() + 
    			"\nAvailable : " + carToDelete.getStatus() + 
    			"\nPrice : " + carToDelete.getPrice()
			);
    		
    		Optional<ButtonType> answer = confirmationAlert.showAndWait();
    		
    		if(answer.get() == ButtonType.OK) {
    			// user clicks OK to delete the car
    			try {
        			Connection connection = MysqlConnection.getDBConnection();
        			String sql = "DELETE FROM `cars` WHERE `cars`.`id` = ?"; 
        			PreparedStatement ps = connection.prepareStatement(sql);
        			ps.setInt(1, carToDelete.getId());
        			ps.execute();
        			
        			// car deleted successfully from database
        			Alert successAlert = new Alert(AlertType.INFORMATION);
        			successAlert.setHeaderText(null);
        			successAlert.setContentText("The car has been deleted successfully.");
        			successAlert.showAndWait();
        			
        			// delete car from TableView
        			carData.remove(index);
        		} catch (Exception e) {
        			// car could not be deleted
        			Alert errorAlert = new Alert(AlertType.ERROR);
        			errorAlert.setHeaderText(null);
        			errorAlert.setContentText("The car could not be delete, please try again.");
        			errorAlert.showAndWait();
        		} 
    		}
    	}
    }
    
    @FXML
    void rentCarBtn_Click(ActionEvent event) throws Exception {
    	// check it a car is selected
    	int index = carTableView.getSelectionModel().getSelectedIndex();
    	if(index >= 0) {
    		// get the selected car
    		Car carToRent = carTableView.getSelectionModel().getSelectedItem();
    		// check if car is available
    		if(carToRent.getStatus()) {
    			// show form for inserting client information that want to rent the car
        		RentCarForm rentCarForm = new RentCarForm(this, carToRent);
        		rentCarForm.showAndWait();
    			// show success alert if added successfully to database (handled internally by RentCarForm)
    			// show error alert if not added to database (handled internally by RentCarForm)
    		}
    		// show error alert if car is not available
    		else {
    			Alert errorAlert = new Alert(AlertType.ERROR);
    			errorAlert.setHeaderText(null);
    			errorAlert.setContentText("This car is not available at the moment.\nPlease choose another car, then try again.");
    			errorAlert.showAndWait();
    		}
    	}
    	// show error alert if no car is selected
    	else {
    		Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText(null);
			errorAlert.setContentText("No car is selected!\nPlease selecte a car to rent.");
			errorAlert.showAndWait();
    	}
    }
    
    @FXML
    void viewRentHistoryBtn_Click(ActionEvent event) {
    	// check it a car is selected
    	int index = carTableView.getSelectionModel().getSelectedIndex();
    	if(index >= 0) {
    		carRentHistoryData.clear();
    		// get all RentedCar records associated with the selected car from rentedCarsData
    		Car carToViewHistory = carTableView.getSelectionModel().getSelectedItem();
    		Object[] rentedCarRecords = rentedCarsData.filtered(rc -> rc.getCar().getId() == carToViewHistory.getId()).toArray();
    		for(Object rc: rentedCarRecords) {
    			carRentHistoryData.add((RentedCar)rc);
    		}
    		// show selected car information
    		rhRegestrationNumber.setText(carToViewHistory.getRegestrationNumber());
    		rhBrand.setText(carToViewHistory.getBrand());
    		rhModel.setText(carToViewHistory.getModel());
    		rhPrice.setText(String.valueOf(carToViewHistory.getPrice()));
    		rhStatus.setText(switch(String.valueOf(carToViewHistory.getStatus())) {
	    		case "true" -> "Available";
	    		case "false" -> "Not Available";
	    		default -> "UN";
    		});
    		
    		// open the tab with rent history of selected car
    		mainTabPane.getTabs().add(carRentHistoryTab);
    	}
    }
    
    @FXML
    void viewClientRentHistoryBtn_Click(ActionEvent event) {
    	// check it a client is selected
    	int index = clientTableView.getSelectionModel().getSelectedIndex();
    	if(index >= 0) {
    		clientRentHistoryData.clear();
    		// get all RentedCar records associated with the selected car from rentedCarsData
    		Client clientToViewHistory = clientTableView.getSelectionModel().getSelectedItem();
    		Object[] rentedCarRecords = rentedCarsData.filtered(rc -> rc.getClient().getCin().compareTo(clientToViewHistory.getCin()) == 0).toArray();
    		for(Object rc: rentedCarRecords) {
    			clientRentHistoryData.add((RentedCar)rc);
    		}
    		// show selected client information
    		clHistoryClientFirstName.setText(clientToViewHistory.getFirstName());
    		clHistoryClientLastName.setText(clientToViewHistory.getLastName());
    		clHistoryClientCin.setText(clientToViewHistory.getCin());
    		clHistoryClientPhoneNumber.setText(clientToViewHistory.getPhoneNumber());
    		clHistoryClientEmail.setText(clientToViewHistory.getEmail());
    		clHistoryClientAddress.setText(clientToViewHistory.getAddress());
    		// open the tab with rent history of selected client
    		mainTabPane.getTabs().add(clientRentHistoryTab);
    	}
    }
    
    @FXML
    void newClientBtn_Click(ActionEvent event) {
    	
    }
    
    @FXML
    void editClientBtn_Click(ActionEvent event) throws Exception {
    	int index = clientTableView.getSelectionModel().getSelectedIndex();
    	if(index >= 0) {
    		Client carToEdit = clientTableView.getSelectionModel().getSelectedItem();
    		EditClientForm editClientForm = new EditClientForm(this, carToEdit);
    		editClientForm.showAndWait();
    	}
    }
    
    @FXML
    void deleteClientBtn_Click(ActionEvent event) {
    	int index = clientTableView.getSelectionModel().getSelectedIndex();
    	if(index >= 0) {
    		Client clientToDelete = clientTableView.getSelectionModel().getSelectedItem();
    		
    		// show confirmation alert
    		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
    		confirmationAlert.setHeaderText("Confirm that you want to delete this client.");
    		confirmationAlert.setContentText("Firstname : " + clientToDelete.getFirstName() +
    				"\nLastname : " + clientToDelete.getLastName() +
    				"\nCIN : " + clientToDelete.getCin() +
    				"\nPhone number : " + clientToDelete.getPhoneNumber() +
    				"\nEmail : " + clientToDelete.getEmail() +
    				"\nAddress : " + clientToDelete.getAddress()
			);
    		
    		Optional<ButtonType> answer = confirmationAlert.showAndWait();
    		
    		if(answer.get() == ButtonType.OK) {
    			// user clicks OK to delete the car
    			try {
        			Connection connection = MysqlConnection.getDBConnection();
        			String sql = "DELETE FROM `clients` WHERE `clients`.`id` = ?"; 
        			PreparedStatement ps = connection.prepareStatement(sql);
        			ps.setInt(1, clientToDelete.getId());
        			ps.execute();
        			
        			// car deleted successfully from database
        			Alert successAlert = new Alert(AlertType.INFORMATION);
        			successAlert.setHeaderText(null);
        			successAlert.setContentText("The client has been deleted successfully.");
        			successAlert.showAndWait();
        			
        			// delete car from TableView
        			clientData.remove(index);
        		} catch (Exception e) {
        			// car could not be deleted
        			Alert errorAlert = new Alert(AlertType.ERROR);
        			errorAlert.setHeaderText(null);
        			errorAlert.setContentText("The client could not be delete, please try again.");
        			errorAlert.showAndWait();
        		} 
    		}
    	}

    }
    
    @FXML
    void rentedCarDetailBtn_Click(ActionEvent event) throws Exception {
    	int index = rentedCarsTableView.getSelectionModel().getSelectedIndex();
    	if(index >= 0) {
    		RentedCar carToView = rentedCarsTableView.getSelectionModel().getSelectedItem();
    		RentedCarDetails detailDialog = new RentedCarDetails(carToView);
    		detailDialog.showAndWait();
    	}
    }
}
