package application.view;

import application.main.*;
import java.io.IOException;
import java.util.logging.Logger;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * @author David Ping
 * @version 1.00 Last edited: 24/04/2017
 */

public class LoginPageController {
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Label invalid;

	private MainApp mainApp;
	private static final Logger LOGGER = Logger.getLogger("MyLog");

	public LoginPageController() {
	}

	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void handleLoginClicked() {
		LOGGER.info("Login selected.");
		Customer customer = new Customer();
		Owner owner = new Owner();
		Admin admin = new Admin();
		Facade facade = new Facade(usernameField.getText(), passwordField.getText());
		
		if(facade.login(usernameField.getText(), passwordField.getText())== false) {
			invalid.setText("Invalid username/password");
			invalid.setTextFill(Color.RED);
		}
	}

	@FXML
	private void handleRegisterClicked() {
		LOGGER.info("Register selected.");
		MainApp main = new MainApp();
		Writer writer = new Writer();
		Customer customer = new Customer("", "", "", "", "", "");
		boolean okClicked = mainApp.showRegistrationPage(customer);
		invalid.setText("");
		if (okClicked) {
			mainApp.getCustomerArray().add(customer);
		}
		try {
			writer.save(main.getCustomerArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
