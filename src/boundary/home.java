package boundary;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scf.control.LancamentoControl;
import scf.entity.Lancamento;

public class home extends Application implements EventHandler<ActionEvent>{
	private ObservableList<String> tamanhos 
	= FXCollections.observableArrayList("pequeno", "medio", "grande");

	private TextField txtId = new TextField();
	private TextField txtSabor = new TextField();
	private TextField txtPreco = new TextField();
	private TextField txtIngredientes = new TextField();
	private ComboBox<String> cmbTamanho = new ComboBox<>(tamanhos);
	private TextField txtFabricacao = new TextField();
	private Button btnSalvar = new Button("Salvar");
	private Button btnPesquisar = new Button("Pesquisar");

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private LancamentoControl control = new LancamentoControl ();
	
	private TableView<Lancamento> tableView = new TableView<>();	
	@Override
	public void start(Stage stage) throws Exception {
		VBox box = new VBox();
		GridPane grid = new GridPane();
		Scene scene = new Scene(box, 300, 300);
		box.getChildren().addAll(grid, tableView);
		tableView.setStyle(STYLESHEET_MODENA);
		
		createTableColumns();
		
		grid.add(new Label("SISTEMA DE CONTROLE FINANCEIRO"), 0, 0);
//		grid.add(txtId, 1, 0);
//		grid.add(new Label("Sabor"), 0, 1);
//		grid.add(txtSabor, 1, 1);
//		grid.add(new Label("Preço"), 0, 2);
//		grid.add(txtPreco, 1, 2);
//		grid.add(new Label("Ingredientes"), 0, 3);
//		grid.add(txtIngredientes, 1, 3);
//		grid.add(new Label("Tamanho"), 0, 4);
//		grid.add(cmbTamanho, 1, 4);
//		grid.add(new Label("Fabricacao"), 0, 5);
//		grid.add(txtFabricacao, 1, 5);
//		grid.add(btnSalvar, 0, 6);
//		grid.add(btnPesquisar, 1, 6);
//		
		btnSalvar.addEventFilter(ActionEvent.ACTION, this);
		// btnPesquisar.setOnAction(this);
		btnPesquisar.addEventFilter(ActionEvent.ACTION, this);
		
		
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

	private void LancamentoToBoundary(Lancamento p) {
		txtId.setText( String.valueOf(p.getIdCat()) );

	
	}

	private Lancamento boundaryToLancamento() {
		Lancamento p = new Lancamento();
		p.setDescricao( txtSabor.getText() );
		p.setIdCat(Integer.parseInt(txtIngredientes.getText()));
		
		return p;
	}
	
	private void createTableColumns() { 
		tableView.setItems( control.getDataList() );
		tableView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Lancamento>() {
			@Override
			public void changed(ObservableValue<? extends Lancamento> p, Lancamento p1, Lancamento p2) {
				if (p2 != null) { 
					LancamentoToBoundary(p2);
				}
			} 
		});
		TableColumn<Lancamento, Number> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory( 
				item -> new ReadOnlyLongWrapper(item.getValue().getIdCat()));
		
		TableColumn<Lancamento, String> saborColumn = new TableColumn<>("Sabor");
		saborColumn.setCellValueFactory( 
				item -> new ReadOnlyStringWrapper(item.getValue().getDescricao()));
		
		TableColumn<Lancamento, Double> precoColumn = new TableColumn<>("Preço");
		precoColumn.setCellValueFactory(
				new PropertyValueFactory<Lancamento, Double>("preco"));
		
		TableColumn<Lancamento, String> fabricColumn = new TableColumn<>("Fabricação");
		fabricColumn.setCellValueFactory( 
				item -> new ReadOnlyStringWrapper(sdf.format(item.getValue().getTpLancamento())));		
		
		
		tableView.getColumns().addAll(idColumn, saborColumn, precoColumn, fabricColumn);
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
