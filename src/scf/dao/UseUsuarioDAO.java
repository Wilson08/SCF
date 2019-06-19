package scf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import scf.entity.Usuario;

public class UseUsuarioDAO implements IUsuarioDao {
	private static Usuario u = new Usuario();

	public static Usuario getUser() {
		return u;
	}

	@Override
	public boolean existe(String usuario) throws DAOException {
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt;
			String sql = "SELECT * from usuario";
			stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("nomeUser").equals(usuario)) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
		return false;
	}

	@Override
	public boolean add(String usuario, String senha) throws DAOException {
		if (existe(usuario)) {
			JOptionPane.showMessageDialog(null, "Usuário já existe, se está tentando fazer login clique em login");
			return false;
		} 
		else {
			try {
				Connection con = ConnectionManager.getInstance().getConnection();
				PreparedStatement stmt;
				String sql = "INSERT INTO usuario" + "(nomeUser, senhaUser) VALUES" + " (?,?)";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, usuario);
				stmt.setString(2, senha);
				stmt.executeUpdate();

				String sql2 = "SELECT idUser FROM usuario";
				stmt = con.prepareStatement(sql2);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					u.setId(rs.getInt("idUser"));
					u.setNome(usuario);
					u.setPass(senha);
					return true;
				}
				con.close();
			} catch (SQLException e) {
				System.out.println("Erro na conexão com o banco");
				e.printStackTrace();
				throw new DAOException(e);
			}
		}
		return false;
	}

	@Override
	public boolean logIn(String usuario, String senha) throws DAOException {
		if (!existe(usuario)) {
			JOptionPane.showMessageDialog(null,
					"Usuário não existe, se está tentando fazer cadastro clique em cadastrar");
			return false;
		} else {
			try {
				Connection con = ConnectionManager.getInstance().getConnection();
				PreparedStatement stmt;
				String sql = "select * from usuario";
				stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (rs.getString("nomeUser").equals(usuario) && rs.getString("senhaUser").equals(senha)) {
						u.setId(rs.getInt("idUser"));
						u.setNome(usuario);
						u.setPass(senha);
						return true;
					}
				}
				stmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				System.out.println("Erro na conexão com o banco");
				e.printStackTrace();
				throw new DAOException(e);
			}

		}
		return false;

	}

}
