package Admin;

import java.beans.Statement;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminController implements Initializable {

	/*
	 * all of the fields within Admin.fxml
	 */
	@FXML
	private TextField id;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private TextField email;
	@FXML
	private DatePicker dob;
	
	@FXML
	private TableView<StudentData> studenttable;
	
	@FXML
	private TableColumn<StudentData, String> idcolumn;
	@FXML
	private TableColumn<StudentData, String> firstnamecolumn;
	@FXML
	private TableColumn<StudentData, String> lastnamecolumn;
	@FXML
	private TableColumn<StudentData, String> emailcolumn;
	@FXML
	private TableColumn<StudentData, String> dobcolumn;
	
	// create instance of the connection from dbConnection
	private dbConnection dc;
	
	//private String rowID = id.getOnMouseClicked().toString();
	
	private ObservableList<StudentData> data;
	private Button bt;
	private String valueToDelete;
	
	// buttons
	
	
	private String sql = "SELECT * FROM students";
	
	public void initialize(URL url, ResourceBundle rb) {
		
		this.dc = new dbConnection();
	}
	
	@FXML
	private void loadStudentData(ActionEvent event) {
		
		try {
			
			// get connection
			Connection conn = dbConnection.getConnection();
			
			this.data = FXCollections.observableArrayList();
			
			// execute our query
			ResultSet rs = conn.createStatement().executeQuery(sql);
			
			// while we have more table data
			while(rs.next()) {
				
				// add the data to each column
				this.data.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
				
			}
			
		} catch (SQLException ex) {
			
			System.err.println("Error" + ex);
		}
		
		//display data to the tables
		this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("ID"));
		this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
		this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
		this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
		this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("DOB"));
		
		this.studenttable.setItems(null);
		this.studenttable.setItems(this.data);
	}
	
	// add data to the database
	@FXML
	private void addStudent(ActionEvent event) {
		
		// sql query to insert data into students at ID, first name, last name, email and DOB
		String sqlInsert = "INSERT INTO students(id,fname,lname,email,DOB) VALUES (?,?,?,?,?)";
		
		try {
			
			Connection conn = dbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlInsert);
			
			// add the data in the right column
			stmt.setString(1, this.id.getText());
			stmt.setString(2, this.firstname.getText());
			stmt.setString(3, this.lastname.getText());
			stmt.setString(4, this.email.getText());
			stmt.setString(5, this.dob.getEditor().getText());
			
			stmt.execute();
			conn.close();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		// update the table after adding and clear fields
		loadStudentData(event);
		clearFields(event);

	}
	
	// clear the form
	@FXML
	private void clearFields(ActionEvent event) {
		this.id.setText("");
		this.firstname.setText("");
		this.lastname.setText("");
		this.email.setText("");
		this.dob.setValue(null);
	}
	
	@FXML
	private void removeStudent(ActionEvent event) {

		//valueToDelete = this.id.getText();
		
		//System.out.println(valueToDelete);
		try {
			
			// sql query to delete data from the database
			String sqlRemove = "DELETE FROM students WHERE id = ?";
			
			Connection conn = dbConnection.getConnection();
			PreparedStatement delete = conn.prepareStatement(sqlRemove);

			
			delete.setString(1, selectStudent());
			
			delete.executeUpdate();
			conn.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		// update table after deleting
		loadStudentData(event);
		
	}
	
	private String selectStudent() {
		
		String result = "";
		
		try {

			String sqlSelect = "SELECT id FROM students";
		
			Connection conn = dbConnection.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(sqlSelect);
			
			result = rs.getString(1);
			conn.close();
			
		} catch (SQLException ex) {
			
			ex.printStackTrace();
		}
		
		return result;
		
	}
	

}
