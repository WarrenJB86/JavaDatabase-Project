package loginapp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
		
		// back to our enum options
		this.combobox.setItems(FXCollections.observableArrayList(option.values()));
		
	}

	
	
}
