package boundary;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import scf.control.LancamentoControl;
import scf.entity.Lancamento;

public class home extends Application implements EventHandler<ActionEvent>{
	
	private Button btnBottomAdd = new Button("Adicionar");
	private Button btnBottomEditar = new Button("Editar    ");
	private Button btnBottomDeletar = new Button("Deletar  ");
	private Label lblTop = new Label("SISTEMA DE CONTROLE FINANCEIRO");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Stage st;
	
	private static LancamentoControl control = new LancamentoControl ();
	private TableView<Lancamento> tableView = new TableView<>();	
	
	@Override
	public void start(Stage stage) throws Exception {

		this.st = stage;
		BorderPane border = new BorderPane();
		tableView.setStyle(STYLESHEET_MODENA);
		Scene scene = new Scene(border, 800, 600);
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
	private void LancamentoToBoundary(Lancamento p) {
//		txtId.setText( String.valueOf(p.getId()) );
//		txtSabor.setText( p.getSabor() );
//		txtIngredientes.setText( p.getIngredientes() );
//		cmbTamanho.setValue( p.getTamanho() );
//		txtPreco.setText( String.format("%6.2f", p.getPreco()) );
//		String strData = sdf.format( p.getFabricacao() );
//		txtFabricacao.setText( strData );
	}
	
	private Lancamento boundaryToLancamento() {
		Lancamento l = new Lancamento();
		l.setIdUsuario(01);
		l.setIdLancamento(01);
		l.setDescricao("this a test");
		l.setTpLancamento(01);
		l.setValor(100.00);
		l.setDtLancamento(new Date(System.currentTimeMillis()));
		l.setIdCat(01);
		return l;
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
				item -> new ReadOnlyLongWrapper(item.getValue().getIdLancamento()));
		
		TableColumn<Lancamento, Number> tipoColumn = new TableColumn<>("Tipo");
		tipoColumn.setCellValueFactory(
				item -> new ReadOnlyIntegerWrapper(item.getValue().getTpLancamento()));
		
		TableColumn<Lancamento, String> descColumn = new TableColumn<>("Descrição");
		descColumn.setCellValueFactory(
				item -> new ReadOnlyStringWrapper(item.getValue().getDescricao()));
		
		TableColumn<Lancamento, Number> valueColumn = new TableColumn<>("Valor");
		valueColumn.setCellValueFactory(
				item -> new ReadOnlyDoubleWrapper(item.getValue().getValor()));
		
		TableColumn<Lancamento, String> dtLancColumn = new TableColumn<>("Data Lanc");
		dtLancColumn.setCellValueFactory(
				item -> new ReadOnlyStringWrapper(sdf.format(item.getValue().getDtLancamento()))
				);
		
		
		tableView.getColumns().addAll(idColumn, descColumn, tipoColumn, valueColumn, dtLancColumn);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnBottomAdd) {
			Lancamento l = boundaryToLancamento();
			control.adicionar(l);
		}
		else if (event.getTarget() == btnBottomEditar) {
			TransacaoBoundary transc = new TransacaoBoundary();
			try {
				transc.start(this.st,this.control);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (event.getTarget() == btnBottomDeletar) {
		}
	}
	
	public void setTeste(Lancamento l) {
		control.adicionar(l);
		
	}
}




