package boundary;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scf.dao.DAOException;
import scf.dao.UseUsuarioDAO;
import scf.entity.Usuario;

public class login extends Application implements EventHandler<ActionEvent> {
	private TextField txtUser = new TextField();
	private PasswordField pwBox = new PasswordField();
	private Button btnEntrar = new Button("Entrar");
	private Button btnCadastrar = new Button("Cadastrar");
	private Stage st;
	private String iniciarServ = "cmd /c java -cp hsqldb.jar org.hsqldb.Server -database.0 SCF -dbname.0 SCF";

	@Override
	public void start(Stage stage) throws Exception {
		VBox box = new VBox();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(40, 40, 40, 40));
		Scene scene = new Scene(box, 300, 200);
		box.getChildren().addAll(grid);
		this.st = stage;

		Label user = new Label("Usuario");
		Label pass = new Label("Senha");
		user.setPrefWidth(100);
		grid.add(user, 0, 1);
		grid.add(txtUser, 1, 1);
		grid.add(pass, 0, 2);
		grid.add(pwBox, 1, 2);
		grid.add(btnEntrar, 0, 3);
		grid.add(btnCadastrar, 1, 3);
		txtUser.setText(null);
		btnEntrar.addEventFilter(ActionEvent.ACTION, this);
		btnCadastrar.addEventFilter(ActionEvent.ACTION, this);

		Runtime.getRuntime().exec(iniciarServ);

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
		UseUsuarioDAO userDAO = new UseUsuarioDAO();
		if (event.getTarget() == btnEntrar) {
			try {
				if (txtUser.getText() == null || pwBox.getText() == null) {
					JOptionPane.showMessageDialog(null, "Usuário inválido");
				} else if (txtUser.getText().equals("admin") || userDAO.logIn(txtUser.getText(), pwBox.getText())) {
					home home = new home();
					try {
						home.start(this.st);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (DAOException e) {
				e.printStackTrace();
			}
		} else if (event.getTarget() == btnCadastrar) {
			try {
				if (userDAO.add(txtUser.getText(), pwBox.getText())) {
					home home = new home();
					try {
						home.start(this.st);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

}