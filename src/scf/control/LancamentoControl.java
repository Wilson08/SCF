package scf.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scf.entity.Lancamento;

public class LancamentoControl {
	private List<Lancamento> lista = new ArrayList<>();
	private ObservableList<Lancamento> dataList = FXCollections.observableArrayList();
	private String connectionURL = "";
	private String user = "";
	private String pass = "";

	public LancamentoControl() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			System.out.println("Driver Carregado");
			connectionURL = "jdbc:hsqldb:hsql://localhost/scf";
			user = "SA";
			pass = "";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public ObservableList<Lancamento> getDataList() {
		return dataList;
	}

	public void setDataList(ObservableList<Lancamento> dataList) {
		this.dataList = dataList;
	}

	public void adicionar(Lancamento l) {
		lista.add(l);
		System.out.println(String.format("Adicionado lancamento %s na lista, tamanho: %d ", l, lista.size()));
		dataList.clear();
		dataList.addAll(lista);
		try {
			Connection con = DriverManager.getConnection(connectionURL, user, pass);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String sql = "INSERT INTO lancamento" + "(idU,tipoL,descricaoL,dataL,valorL,idCategoria) VALUES"
					+ " (?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, l.getIdUsuario());
			stmt.setString(2, l.getTpLancamento() == 0? "Despesa" : "Renda");
			stmt.setString(3, l.getDescricao());
			Date data = new Date(l.getDtLancamento().getTime());
			System.out.println(sql);
			stmt.setDate(4, data);
			stmt.setDouble(5, l.getValor());
			stmt.setInt(6, l.getIdCat());
			stmt.executeUpdate();
			con.close();

		} catch (SQLException e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}

	public void deletar(Lancamento l) {
		lista.remove(l);
		System.out.println(String.format("Removendo %s da lista, tamanho: %d", l, lista.size()));
		dataList.clear();
		dataList.addAll(lista);
	}
}
