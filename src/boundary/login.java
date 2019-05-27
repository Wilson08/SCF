package boundary;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox box = new VBox();
		GridPane grid = new GridPane();
		Scene scene = new Scene(box, 300, 300);
		box.getChildren().addAll(grid);
		
		
		
		grid.add(new Label("Usuario"), 0, 0);
		grid.add(txtUser, 1, 0);
		grid.add(new Label("Senha"), 0, 1);
		grid.add(txtPassword, 1, 1);
		grid.add(btnSalvar, 0, 2);
		
		btnSalvar.addEventFilter(ActionEvent.ACTION, this);
		// btnPesquisar.setOnAction(this);
	
		
		stage.setScene(scene);
		stage.setTitle("Gestão de Pizzas");
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
	
		if (event.getTarget() == btnSalvar) { 
			System.out.println("teste");
			}		
		}
		
	}