package scf.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import boundary.login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scf.entity.Lancamento;
import scf.entity.Usuario;

public class UseLancamentoDAO implements ILancamentoDAO {
	private List<Lancamento> lista = new ArrayList<>();
	private ObservableList<Lancamento> dataList = FXCollections.observableArrayList();
	private UseUsuarioDAO ud = new UseUsuarioDAO();
	private Usuario u = ud.getUser();

	@Override
	public List<Lancamento> pesquisaPorNome(String nome) throws DAOException {
		List<Lancamento> lista = new ArrayList<>();
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt;
			System.out.println(u.getId());
			String sql = "SELECT * from lancamento where descricaoL like ? and idU = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + nome + "%");
			stmt.setInt(2, u.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Lancamento l = new Lancamento();
				l.setIdLancamento(rs.getInt("idL"));
				l.setIdUsuario(rs.getInt("idU"));
				l.setDtLancamento(rs.getDate("dataL"));
				l.setIdCat(rs.getInt("idCategoria"));
				l.setDescricao(rs.getString("descricaoL"));
				l.setTpLancamento(rs.getString("tipoL"));
				l.setValor(rs.getDouble("valorL"));
				lista.add(l);
			}
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
		return lista;
	}

	@Override
	public void insert(Lancamento l) throws DAOException {
		lista.add(l);
		dataList.clear();
		dataList.addAll(lista);
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt;
			String sql = "INSERT INTO lancamento" + "(idU,tipoL,descricaoL,valorL,idCategoria,dataL) VALUES"
					+ " (?,?,?,?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, u.getId());
			stmt.setString(2, l.getTpLancamento());
			stmt.setString(3, l.getDescricao());
			stmt.setDouble(4, l.getValor());
			stmt.setInt(5, l.getIdCat());
			Date data = new Date(l.getDtLancamento().getTime());
			stmt.setDate(6, data);
			stmt.executeUpdate();
			con.close();

		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	@Override
	public void edit(Lancamento l, int idL) throws DAOException {
		try {
			System.out.println(l.getIdLancamento());
			Connection con = ConnectionManager.getInstance().getConnection();
			String sqlUpdate = "UPDATE lancamento " + "SET tipoL = ?, descricaoL = ?, "
					+ "dataL = ?, valorL = ?, idCategoria = ?" + " WHERE idL = ?";
			PreparedStatement state = con.prepareStatement(sqlUpdate);
			state.setString(1, l.getTpLancamento());
			state.setString(2, l.getDescricao());
			Date data = new Date(l.getDtLancamento().getTime());
			state.setDate(3, data);
			state.setDouble(4, l.getValor());
			state.setInt(5, l.getIdCat());
			state.setInt(6, idL);
			state.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	@Override
	public void delete(Lancamento l, int idL) throws DAOException {
		lista.remove(l);
		System.out.println(String.format("Removendo %s da lista, tamanho: %d", l, lista.size()));
		dataList.clear();
		dataList.addAll(lista);
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			String sqlUpdate = "DELETE FROM lancamento WHERE idL = ?";
			PreparedStatement state = con.prepareStatement(sqlUpdate);
			state.setInt(1, idL);
			state.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

}