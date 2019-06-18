package boundary;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import scf.entity.Categoria;
import boundary.home;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scf.control.CategoriaControl;
import scf.control.ControlException;
import scf.control.LancamentoControl;
import scf.dao.UseLancamentoDAO;
import scf.dao.UseUsuarioDAO;
import scf.entity.Lancamento;
import scf.entity.Usuario;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.DatePicker;

public class TransacaoBoundary extends Application implements EventHandler<ActionEvent> {
	private ObservableList<String> tipos = FXCollections.observableArrayList("Despesa", "Renda");
	private ObservableList<String> categorias = FXCollections.observableArrayList();

	private TextField txtTransac = new TextField();
	private ComboBox<String> txtCategoria = new ComboBox(categorias);
	private TextField txtValor = new TextField();
	private ComboBox<String> cmpTipos = new ComboBox(tipos);
	private Button btnSalvar = new Button("Salvar");
	private Button btnLimpar = new Button("Limpar");
	private Button botao = new Button("Salvar");
	private Button btnVoltar = new Button("Voltar");
	private DatePicker dataPicker = new DatePicker();
	private Stage st;
	private int toEdit = 0;
	private UseUsuarioDAO ud = new UseUsuarioDAO();
	private Usuario u = ud.getUser();

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private LancamentoControl control;
	private CategoriaControl controlCategoria = new CategoriaControl();

	public void start(Stage stage, LancamentoControl control) throws Exception {
		setCombo();
		this.st = stage;
		this.control = control;
		VBox box = new VBox();
		box.setPadding(new Insets(10, 50, 50, 50));
		box.setSpacing(10);
		Scene scene = new Scene(box, 500, 500);// (box, 400, 400);
		Label lblT = new Label("\t      Nova Transação");
		lblT.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
		box.getChildren().add(lblT);

		box.getChildren().add(new Label("Nome da Transação"));
		box.getChildren().add(txtTransac);
		box.getChildren().add(new Label("Categoria"));
		box.getChildren().add(txtCategoria);
		box.getChildren().add(new Label("Valor"));
		box.getChildren().add(txtValor);
		box.getChildren().add(new Label("Data"));
		box.getChildren().add(dataPicker);
		box.getChildren().add(new Label("Tipos"));
		box.getChildren().add(cmpTipos);
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.getChildren().addAll(btnSalvar, btnLimpar, btnVoltar);
		box.getChildren().add(hb);
		btnSalvar.addEventFilter(ActionEvent.ACTION, this);
		btnLimpar.addEventFilter(ActionEvent.ACTION, this);
		btnVoltar.addEventFilter(ActionEvent.ACTION, this);
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.setTitle("Nova transação");
		stage.show();
	}

	private void setCombo() {
		try {
			controlCategoria.pesquisar("");
			categorias.clear();
			ObservableList<Categoria> data = controlCategoria.getDataList();

			for (Categoria categoria : data) {
				categorias.add(Integer.toString(categoria.getIdCat()));
			}

			txtCategoria.setItems(categorias);

		} catch (ControlException e) {
			e.printStackTrace();
		}
	}

	private Lancamento boundaryToLancamento() {
		Lancamento l = new Lancamento();
		l.setIdUsuario(u.getId());
		l.setDescricao(txtTransac.getText());
		l.setTpLancamento(cmpTipos.getValue());
		l.setIdCat(Integer.parseInt(txtCategoria.getValue()));
		try {
			l.setValor(Double.parseDouble(txtValor.getText()));
			LocalDate ld = dataPicker.getValue();
			Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			Date date = Date.from(instant);
			l.setDtLancamento(date);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return l;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnSalvar) {
			Lancamento l = boundaryToLancamento();
			try {
				control.adicionar(l);
			} catch (ControlException e1) {
				e1.printStackTrace();
			}
			System.out.println(l.getIdCat());
			home hm = new home();
			try {
				hm.start(st);
			} catch (Exception e) {
				e.printStackTrace();
				dialog(AlertType.ERROR, "Erro, favor tentar novamente");
			}
		} else if (event.getTarget() == btnLimpar) {
			txtTransac.setText("");
			txtValor.setText("");
			cmpTipos.setValue(null);
			txtCategoria.setValue(null);
			dataPicker.setValue(null);
		} else if (event.getTarget() == botao) {
			Lancamento l = boundaryToLancamento();
			UseLancamentoDAO ul = new UseLancamentoDAO();
			try {
				ul.edit(l, toEdit);
				home hm = new home();
				hm.start(st);
			} catch (Exception e1) {
				e1.printStackTrace();
				dialog(AlertType.ERROR, "Erro ao pesquisar no sistema");
			}
		} else if (event.getTarget() == btnVoltar) {
			home hm = new home();
			try {
				hm.start(st);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void dialog(AlertType tipo, String texto) {
		Alert alert = new Alert(tipo);
		alert.setTitle("Erro");
		alert.setHeaderText(texto);
		alert.setContentText("Consulte o administrador do sistema");
		alert.showAndWait();

	}

	public void edit(Stage stage, Lancamento l) {
		toEdit = l.getIdLancamento();
		System.out.println(toEdit);
		setCombo();
		this.st = stage;
		VBox box = new VBox();
		box.setPadding(new Insets(10, 50, 50, 50));
		box.setSpacing(10);
		Scene scene = new Scene(box, 500, 600);// (box, 400, 400);
		Label lblT = new Label("\t    Editar Transação");
		lblT.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
		box.getChildren().add(lblT);

		txtTransac.setText(l.getDescricao());
		txtCategoria.setValue(Integer.toString(l.getIdCat()));
		txtValor.setText(Double.toString(l.getValor()));
		dataPicker.setValue(LocalDate.now());
		cmpTipos.setValue(l.getTpLancamento());
		box.getChildren().add(new Label("Nome da Transação"));
		box.getChildren().add(txtTransac);
		box.getChildren().add(new Label("Categoria"));
		box.getChildren().add(txtCategoria);
		box.getChildren().add(new Label("Valor"));
		box.getChildren().add(txtValor);
		box.getChildren().add(new Label("Data"));
		box.getChildren().add(dataPicker);
		box.getChildren().add(new Label("Tipos"));
		box.getChildren().add(cmpTipos);
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.getChildren().addAll(botao, btnLimpar, btnVoltar);
		box.getChildren().add(hb);
		botao.addEventFilter(ActionEvent.ACTION, this);
		btnLimpar.addEventFilter(ActionEvent.ACTION, this);
		btnVoltar.addEventFilter(ActionEvent.ACTION, this);

		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.setTitle("Editar transação");
		stage.show();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}
