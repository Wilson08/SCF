package boundary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import scf.control.ControlException;
import scf.control.LancamentoControl;
import scf.entity.Lancamento;

public class home extends Application implements EventHandler<ActionEvent> {

	private TextField txt = new TextField();
	private Button btnBottomAdd = new Button("Adicionar");
	private Button btnBottomEditar = new Button("Editar    ");
	private Button btnBottomDeletar = new Button("Deletar  ");
	private Label lblTop = new Label("SISTEMA DE CONTROLE FINANCEIRO");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Stage st;

	private static LancamentoControl control = new LancamentoControl();
	private TableView<Lancamento> tableView = new TableView<>();

	@Override
	public void start(Stage stage) throws Exception {
		

		
		control.pesquisar("");
		this.st = stage;
		
		BorderPane border = new BorderPane();
		tableView.setStyle(STYLESHEET_MODENA);
		Scene scene = new Scene(border, 800, 600);
		VBox vboxTop = new VBox();
		HBox box = new HBox();
		box.setSpacing(10);
		
		
		//txt.textProperty().addListener(ObservableValue<? extends Lancamento> p, Lancamento p1, Lancamento p2);
		createTableColumns();
		border.setCenter(tableView);
		BorderPane.setMargin(tableView, new Insets(25, 25, 10, 25));
		BorderPane.setAlignment(tableView, Pos.CENTER);

//		lblTop.setPadding(new Insets(10, 10, 10, 10));
//		border.setTop(lblTop);
//		BorderPane.setMargin(lblTop, new Insets(10, 10, 10, 10));
//		BorderPane.setAlignment(lblTop, Pos.CENTER);

		box.getChildren().addAll(btnBottomAdd, btnBottomEditar, btnBottomDeletar);
		lblTop.setPadding(new Insets(10, 10, 10, 250));
		box.setPadding(new Insets(10, 10, 10, 10));
		vboxTop.getChildren().addAll(lblTop, box);
		border.setTop(vboxTop);
		BorderPane.setAlignment(vboxTop, Pos.CENTER);
		BorderPane.setMargin(vboxTop, new Insets(0, 0, -20, 15));
		
		border.setBottom(txt);
		BorderPane.setAlignment(txt, Pos.CENTER);
		BorderPane.setMargin(txt, new Insets(10, 10, 10, 10));

		btnBottomAdd.addEventFilter(ActionEvent.ACTION, this);
		btnBottomEditar.addEventFilter(ActionEvent.ACTION, this);
		btnBottomDeletar.addEventFilter(ActionEvent.ACTION, this);
		tableView.addEventFilter(ActionEvent.ACTION, this);

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
		double qtdeReceita = 0;
		double qtdeDespesa = 0;
		for (int i = 0; i < tableView.getItems().size(); i++) {
			Lancamento item = tableView.getItems().get(i);
			if(p.getIdCat() == item.getIdCat()) {
				if(p.getTpLancamento() == "0") {
					qtdeReceita += p.getValor();
				}else {
					qtdeReceita += p.getValor(); 
				}
			}
			
		}
		txt.setText("Total de receita com categoria de id "+ p.getIdCat()+" é de : "+qtdeReceita+"                    "+
				"Total de despesas com categoria de id "+p.getIdCat()+" é de : " + qtdeDespesa);
	}

	private Lancamento boundaryToLancamento() {
		Lancamento l = new Lancamento();
		l.setIdUsuario(01);
		l.setIdLancamento(01);
		l.setDescricao("this a test");
		l.setTpLancamento("01");
		l.setValor(100.00);
		l.setDtLancamento(new Date(System.currentTimeMillis()));
		l.setIdCat(01);
		return l;
	}

	private void createTableColumns() {
		tableView.setItems(control.getDataList());
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lancamento>() {
			@Override
			public void changed(ObservableValue<? extends Lancamento> p, Lancamento p1, Lancamento p2) {
				if (p2 != null) {
					LancamentoToBoundary(p2);
					System.out.println(p2.getDescricao());
				}
			}
		});

		TableColumn<Lancamento, Number> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory(item -> new ReadOnlyIntegerWrapper(item.getValue().getIdLancamento()));

		TableColumn<Lancamento, String> tipoColumn = new TableColumn<>("Tipo");
		tipoColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getTpLancamento()));

		TableColumn<Lancamento, String> descColumn = new TableColumn<>("Descrição");
		descColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getDescricao()));

		TableColumn<Lancamento, Number> valueColumn = new TableColumn<>("Valor");
		valueColumn.setCellValueFactory(item -> new ReadOnlyDoubleWrapper(item.getValue().getValor()));

		TableColumn<Lancamento, String> dtLancColumn = new TableColumn<>("Data Lanc");
		dtLancColumn
				.setCellValueFactory(item -> new ReadOnlyStringWrapper(sdf.format(item.getValue().getDtLancamento())));

		tableView.getColumns().addAll(idColumn, descColumn, tipoColumn, valueColumn, dtLancColumn);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnBottomAdd) {
			TransacaoBoundary transc = new TransacaoBoundary();
			try {
				transc.start(this.st, this.control);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (event.getTarget() == btnBottomEditar) {
			Lancamento l = this.tableView.getSelectionModel().getSelectedItem();
			TransacaoBoundary transc = new TransacaoBoundary();
			transc.edit(st, l);
		} else if (event.getTarget() == btnBottomDeletar) {
			Lancamento l = this.tableView.getSelectionModel().getSelectedItem();
			try {
				control.deletar(l);
			} catch (ControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(event.getTarget() == tableView) {
			System.out.println("AEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
		}
	}

	public void setTeste(Lancamento l) throws ControlException {
		control.adicionar(l);

	}
}
