package scf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scf.entity.Categoria;

public class UseCategoriaDAO implements ICategoriaDAO{
	private List<Categoria> lista = new ArrayList<>();
	private ObservableList<Categoria> dataList = FXCollections.observableArrayList();

	@Override
	public List<Categoria> pesquisar(String nome) throws DAOException {
		List<Categoria> lista = new ArrayList<>();
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt;
			String sql = "SELECT * FROM CATEGORIA";
			stmt = con.prepareStatement(sql);
			ResultSet  rs = stmt.executeQuery();
			while(rs.next()) {
				Categoria c = new Categoria();
				c.setIdCat(rs.getInt("IDCAT"));
				c.setNomeCat(rs.getString("NOMECAT"));
				lista.add(c);
			}
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
		return lista;
	}

	@Override
	public void insert(Categoria categoria) throws DAOException {
		lista.add(categoria);
		dataList.clear();
		dataList.addAll(lista);
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt;
			String sql = "INSERT INTO CATEGORIA " + " (NOMECAT) VALUES"
					+ " (?)";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, categoria.getNomeCat());
			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
	}	
	

	@Override
	public void delete(Categoria categoria, int IDCAT) throws DAOException {
		lista.remove(categoria);
		System.out.println(String.format("Removendo %s da lista, tamanho: %d", categoria, lista.size()));
		dataList.clear();
		dataList.addAll(lista);
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			String sqlUpdate = "DELETE FROM CATEGORIA WHERE IDCAT = ?";
			PreparedStatement state = con.prepareStatement(sqlUpdate);
			state.setInt(1, IDCAT);
			state.executeUpdate();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
}
