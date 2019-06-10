package scf.dao;

import java.util.List;
import scf.entity.Categoria;

public interface ICategoriaDAO {
	List<Categoria> pesquisar(String nome) throws DAOException;
	void insert(Categoria categoria) throws DAOException;
	void delete(Categoria categoria, int idC) throws DAOException;
}
