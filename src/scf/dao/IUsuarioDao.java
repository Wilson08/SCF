package scf.dao;

import scf.entity.Usuario;

public interface IUsuarioDao {

	boolean existe(String usuario) throws DAOException;

	boolean add(String usuario, String senha) throws DAOException;

	boolean logIn(String usuario, String senha) throws DAOException;
}
