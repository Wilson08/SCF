package boundary;


import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scf.control.CategoriaControl;
import scf.entity.Categoria;

public class CategoriaBoundary  extends Application implements EventHandler<ActionEvent> {

	private Button btnBottomAdd = new Button("Adicionar");
	private Button btnBottomDeletar = new Button("Deletar");
	private Button btnBack = new Button ("Voltar");
	private Label lblTop = new Label("Cadastro de Categoria");
	private Stage st;

	private static CategoriaControl control = new CategoriaControl();
	private TableView<Categoria> tableView = new TableView<>();

	@Override
	public void start(Stage stage) throws Exception {
		
		control.pesquisar("");
		this.st = stage;
		
		tableView.setStyle(STYLESHEET_MODENA);
		VBox vbox = new VBox();
		HBox box = new HBox();
		Scene scene = new Scene(vbox, 400, 200);
		
		createTableColumns();
		
		vbox.getChildren().addAll(lblTop, tableView, box);

		box.setSpacing(10);
		box.getChildren().addAll(btnBottomAdd,  btnBottomDeletar, btnBack);

		btnBottomAdd.addEventFilter(ActionEvent.ACTION, this);
		btnBottomDeletar.addEventFilter(ActionEvent.ACTION, this);
		btnBack.addEventFilter(ActionEvent.ACTION, this);
		tableView.addEventFilter(ActionEvent.ACTION, this);

		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.setTitle("Gestão de Categorias");
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void dialog(AlertType tipo, String texto) {
		Alert alert = new Alert(tipo);
		alert.setTitle("Erro");
		alert.setHeaderText(texto);
		alert.setContentText("Consulte o administrador do sistema");
		alert.showAndWait();

	}

	private void CategoriaToBoundary(Categoria c) {
	}


	private void createTableColumns() {
		tableView.setItems(control.getDataList());
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Categoria>() {
			@Override
			public void changed(ObservableValue<? extends Categoria> c, Categoria c1, Categoria c2) {
				if (c2 != null) {
					CategoriaToBoundary(c2);
					System.out.println(c2.getNomeCat());
				}
			}
		});

		TableColumn<Categoria, Number> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory(item -> new ReadOnlyIntegerWrapper(item.getValue().getIdCat()));

		TableColumn<Categoria, String> categoriaColumn = new TableColumn<>("Categoria");
		categoriaColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getNomeCat()));


		tableView.getColumns().addAll(idColumn, categoriaColumn);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnBottomAdd) {
			try {
				Categoria c = new Categoria();
				String nomeCat = JOptionPane.showInputDialog("Digite o nome da nova categoria");
				c.setNomeCat(nomeCat);
				control.adicionar(c);
				home hm = new home();
				hm.start(st);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (event.getTarget() == btnBottomDeletar) {
			Categoria c = this.tableView.getSelectionModel().getSelectedItem();
			try {
				control.deletar(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (event.getTarget() == btnBack){
			home hm = new home();
			try {
				hm.start(st);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
