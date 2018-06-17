package loginapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Admin.AdminController;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import students.StudentsController;
import javafx.event.*;

public class LoginController implements Initializable {
	
	// create instance of LoginModel
	LoginModel loginModel = new LoginModel();

	// labels and buttons in the login page
	@FXML
	private Label dbstatus;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private ComboBox<option> combobox;
	@FXML
	private Button loginButton;
	@FXML
	private Label loginStatus;
	
	// initialize connection to database
	public void initialize(URL url, ResourceBundle rb) {
		
		// check if we're connected
		if(this.loginModel.isDatabaseConnected()) {
			
			// if so, display "Connected"
			this.dbstatus.setText("Connected");
		} else {
			
			// if not, then display not connected
			this.dbstatus.setText("Not Connected");
		}
		
		// set options from the enum
		this.combobox.setItems(FXCollections.observableArrayList(option.values()));
		
	}
	
	// login function
	@FXML
	public void Login(ActionEvent event) {
		
		try {
			// check login information
			if(this.loginModel.isLogin(this.username.getText(), this.password.getText(), ((option)this.combobox.getValue()).toString())) {
				
				Stage stage = (Stage)this.loginButton.getScene().getWindow();
				stage.close();
				
				switch(((option) this.combobox.getValue()).toString()) 
				{
					case "Admin":
						adminLogin();
						break;
					case "Student":
						studentLogin();
						break;
				} 
			}
			else {
				this.loginStatus.setText("Wrong!");
			}	
			
		} catch(Exception localException) {
			
			
		}
	}
	
	
	// login method for student
	public void studentLogin() {
		
		try {
			Stage userStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			// create new root and find FXML file
			Pane root = (Pane) loader.load(getClass().getResource("/students/studentFXML.fxml").openStream());
			
			StudentsController studentController = (StudentsController) loader.getController();
			
			Scene scene = new Scene(root);
			
			userStage.setScene(scene);
			userStage.setTitle("Student Dashboard");
			
			// prevent students from resizing
			userStage.setResizable(false);
			userStage.show();
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	// login method for the admin
	public void adminLogin() {
		
		try {
			Stage adminStage = new Stage();
			FXMLLoader adminLoader = new FXMLLoader();
			
			// create new admin root and find the FXML file
			Pane adminRoot = (Pane) adminLoader.load(getClass().getResource("/Admin/Admin.fxml").openStream());
			
			AdminController adminController = (AdminController) adminLoader.getController();
			
			Scene scene = new Scene(adminRoot);
			
			adminStage.setScene(scene);
			adminStage.setTitle("Admin Dashboard");
			
			adminStage.setResizable(false);
			adminStage.show();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	
	
}
