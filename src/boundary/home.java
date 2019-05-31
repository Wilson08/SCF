package boundary;


import javafx.application.Application;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import scf.control.LancamentoControl;
import scf.entity.Lancamento;

public class home extends Application implements EventHandler<ActionEvent>{
	
	private Button btnBottomAdd = new Button("Adicionar");
	private Button btnBottomEditar = new Button("Editar    ");
	private Button btnBottomDeletar = new Button("Deletar  ");
	private Label lblTop = new Label("SISTEMA DE CONTROLE FINANCEIRO");
	
	private LancamentoControl control = new LancamentoControl ();
	private TableView<Lancamento> tableView = new TableView<>();	
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane border = new BorderPane();
		tableView.setStyle(STYLESHEET_MODENA);
		Scene scene = new Scene(border, 1300, 800);
		HBox box = new HBox();
		box.setSpacing(10);
		
		
		createTableColumns();
		border.setCenter(tableView);
		BorderPane.setMargin(tableView, new Insets(25, 25, 10, 25));
		BorderPane.setAlignment(tableView, Pos.CENTER);
		
		// TOP
		lblTop.setPadding(new Insets(10, 10, 10, 10));
		border.setTop(lblTop);
		BorderPane.setMargin(lblTop, new Insets(10, 10, 10, 10));
		BorderPane.setAlignment(lblTop, Pos.CENTER);
		
	      // BOTTOM
	    box.getChildren().addAll( btnBottomAdd, btnBottomEditar, btnBottomDeletar );
		border.setBottom(box);
	    BorderPane.setAlignment(box, Pos.BOTTOM_LEFT);
	    BorderPane.setMargin(box, new Insets(10, 10, 30, 40));
	    
	    btnBottomAdd.addEventFilter(ActionEvent.ACTION, this);
	    btnBottomEditar.addEventFilter(ActionEvent.ACTION, this);
	    btnBottomDeletar.addEventFilter(ActionEvent.ACTION, this);

	    stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.setTitle("Gestão de Lancamentos");
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

	
	private void createTableColumns() { 
		tableView.setItems( control.getDataList() );
		tableView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Lancamento>() {
			@Override
			public void changed(ObservableValue<? extends Lancamento> p, Lancamento p1, Lancamento p2) {
				if (p2 != null) { 
					//LancamentoToBoundary(p2);
				}
			} 
		});
		TableColumn<Lancamento, Number> idColumn = new TableColumn<>("Id");		
		TableColumn<Lancamento, String> tipoColumn = new TableColumn<>("Tipo");
		TableColumn<Lancamento, Double> precoColumn = new TableColumn<>("Valor");
		TableColumn<Lancamento, String> dtLancColumn = new TableColumn<>("Data Lanc");	
		
		
		tableView.getColumns().addAll(idColumn, tipoColumn, precoColumn, dtLancColumn);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnBottomAdd) {
			System.out.println("Você clicou no botão de adicionar");
		}
		else if (event.getTarget() == btnBottomEditar) {
			System.out.println("Você clicou no botão de editar");
		}
		else if (event.getTarget() == btnBottomDeletar) {
			System.out.println("Você clicou no botão de deletar");
		}
	}
}
