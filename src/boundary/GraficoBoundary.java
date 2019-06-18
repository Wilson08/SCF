package boundary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scf.dao.ConnectionManager;
import scf.dao.DAOException;
import scf.dao.UseUsuarioDAO;
import scf.entity.Usuario;

public class GraficoBoundary extends Application {
	private UseUsuarioDAO ud = new UseUsuarioDAO();
	private Usuario u = ud.getUser();
	private Button btnBack = new Button("Voltar");
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Gráfico");

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Intervalo");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Valores");

		BarChart barChart = new BarChart(xAxis, yAxis);

		XYChart.Series dataSeries1 = buildDespesa();
		dataSeries1.setName("Despesa");

		XYChart.Series dataSeries2 = buildRenda();
		dataSeries2.setName("Renda");


		barChart.getData().addAll(dataSeries1, dataSeries2);

		VBox vbox = new VBox(barChart);

		Scene scene = new Scene(vbox, 200, 200);

		primaryStage.setScene(scene);

		primaryStage.show();
		vbox.getChildren().add(btnBack);
		btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				home hm = new home();
				try {
					hm.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}
	
	@SuppressWarnings("rawtypes")
	public XYChart.Series buildDespesa() throws DAOException {
		XYChart.Series dataSeries1 = new XYChart.Series();
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			String sqlUpdate = "SELECT SUM(valorL) as valorL, "
					+ "CONCAT(MONTH(dataL),'/',(YEAR(dataL))) as data"
					+ " FROM lancamento WHERE idU = ? AND tipoL LIKE '%Despesa%' "
					+ "GROUP BY dataL";
			PreparedStatement state = con.prepareStatement(sqlUpdate);
			state.setInt(1, u.getId());
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				dataSeries1.getData().add(new XYChart.Data(rs.getString("data"), rs.getDouble("valorL")));
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
		return dataSeries1;
	}
	
	public XYChart.Series buildRenda() throws DAOException {
		XYChart.Series dataSeries1 = new XYChart.Series();
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			String sqlUpdate = "SELECT SUM(valorL) as valorL, "
					+ "CONCAT(MONTH(dataL),'/',(YEAR(dataL))) as data"
					+ " FROM lancamento WHERE idU = ? AND tipoL LIKE '%Despesa%' "
					+ "GROUP BY dataL";
			PreparedStatement state = con.prepareStatement(sqlUpdate);
			state.setInt(1, u.getId());
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				dataSeries1.getData().add(new XYChart.Data(rs.getString("data"), rs.getDouble("valorL")));
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
		return dataSeries1;
	}
}
