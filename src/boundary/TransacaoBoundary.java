package boundary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import boundary.home;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scf.control.LancamentoControl;
import scf.entity.Lancamento;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.DatePicker;

public class TransacaoBoundary extends Application implements EventHandler<ActionEvent>{
	private ObservableList<String> tipos 
	= FXCollections.observableArrayList("Despesa", "Renda");

	private TextField txtTransac = new TextField();
	private ComboBox<String> txtCategoria = new ComboBox();
	private TextField txtValor = new TextField();
	private ComboBox<String> cmpTipos = new ComboBox(tipos);
	private Button btnSalvar = new Button("Salvar");
	private Button btnPesquisar = new Button("Pesquisar");
	private DatePicker dataPicker = new DatePicker();
	private Stage st;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private LancamentoControl control;
	
	public void start(Stage stage, LancamentoControl control) throws Exception {
		this.st = stage;
		this.control = control;
		VBox box = new VBox();
		box.setPadding(new Insets(10, 50, 50, 50));
	    box.setSpacing(10);
		Scene scene = new Scene(box, 800, 800);//(box, 400, 400);
		Label lblT = new Label("\t    Nova Transação");
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
		box.getChildren().add(btnSalvar);
		box.getChildren().add(btnPesquisar);
		btnSalvar.addEventFilter(ActionEvent.ACTION, this);
		btnPesquisar.addEventFilter(ActionEvent.ACTION, this);
		
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.setTitle("Nova transação");
		stage.show();
	}
	
	private Lancamento boundaryToLancamento() {
		Lancamento l = new Lancamento();
		l.setIdUsuario(01);
		l.setIdLancamento(01);
		l.setDescricao(txtTransac.getText());
		l.setTpLancamento(cmpTipos.getValue() == "Despesa" ? 0 : 1);
		try {
			l.setValor(Double.parseDouble(txtValor.getText()));
			l.setIdCat(Integer.parseInt(txtCategoria.getValue() == null ? "0" : txtCategoria.getValue()));
			LocalDate ld = dataPicker.getValue();
			Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			Date date = Date.from(instant);
			l.setDtLancamento(date);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
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
			control.adicionar(l);
		}
		else if(event.getTarget() == btnPesquisar) {
			home uhome = new home();
			Lancamento l = boundaryToLancamento();
			uhome.setTeste(l);
			try {
				uhome.start(st);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}

