package boundary;

import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scf.control.ControlException;
import scf.control.LancamentoControl;
import scf.entity.Lancamento;

public class home extends Application implements EventHandler<ActionEvent> {
	private TextField txt = new TextField();
	private Button btnBottomAdd = new Button("Adicionar");
	private Button btnCategoria = new Button("Categoria");
	private Button btnBottomEditar = new Button("Editar    ");
	private Button btnBottomDeletar = new Button("Deletar  ");
	private Button btnGrafico = new Button("Exibir Gráfico");
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

		createTableColumns();
		border.setCenter(tableView);
		BorderPane.setMargin(tableView, new Insets(25, 25, 10, 25));
		BorderPane.setAlignment(tableView, Pos.CENTER);

		box.getChildren().addAll(btnBottomAdd, btnBottomEditar, btnBottomDeletar, btnCategoria, btnGrafico);
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
		btnCategoria.addEventFilter(ActionEvent.ACTION, this);
		btnGrafico.addEventFilter(ActionEvent.ACTION, this);
		tableView.addEventFilter(ActionEvent.ACTION, this);

		stage.setHeight(600);
		stage.setWidth(800);
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
		ObservableList<Lancamento> data = control.getDataList();
		for (Lancamento lancamento : data) {
			if ((p.getIdCat() == lancamento.getIdCat())) {
				if (p.getTpLancamento().equals("Despesa")) {
					qtdeDespesa += lancamento.getValor();
				} else {
					qtdeReceita = qtdeReceita + lancamento.getValor();
				}
			}
		}
		String despesa = "";
		despesa = Double.toString(qtdeDespesa);
		txt.setText("Total de receita com categoria de id " + p.getIdCat() + " é de : " + qtdeReceita
				+ "                    " + "Total de despesas com categoria de id " + p.getIdCat() + " é de : "
				+ despesa);
	}

	private void createTableColumns() {
		tableView.setItems(control.getDataList());
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lancamento>() {
			@Override
			public void changed(ObservableValue<? extends Lancamento> p, Lancamento p1, Lancamento p2) {
				if (p2 != null) {
					LancamentoToBoundary(p2);
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
				e.printStackTrace();
			}
		} else if (event.getTarget() == btnCategoria) {
			CategoriaBoundary categoriaBoundary = new CategoriaBoundary();
			try {
				categoriaBoundary.start(this.st);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else if (event.getTarget() == btnGrafico) {
			GraficoBoundary gb = new GraficoBoundary();
			try {
				gb.start(this.st);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
