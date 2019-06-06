package boundary;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class login extends Application implements EventHandler<ActionEvent> {
	private TextField txtUser = new TextField();
	private TextField txtPassword = new TextField();
	private Button btnSalvar = new Button("Entrar");
	private Stage st;
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox box = new VBox();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(40, 40, 40, 40));
		Scene scene = new Scene(box, 300, 275);
		box.getChildren().addAll(grid);
		this.st = stage;
		
		
		grid.add(new Label("Usuario"), 0, 1);
		grid.add(txtUser, 1, 1);
		grid.add(new Label("Senha"), 0, 2);
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		grid.add(btnSalvar, 1, 3);
		
		btnSalvar.addEventFilter(ActionEvent.ACTION, this);

		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.setTitle("SCF - Sistema Controle Financeiro");
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
	
		if (event.getTarget() == btnSalvar) { 
				if (txtUser.getText().equals("admin") || txtUser.equals("wilson") || txtUser.equals("gerente") && txtPassword.equals("123")) {
					home home = new home();
					try {
						home.start(this.st);
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
				}
			}		
		}
		
	}