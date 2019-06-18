package scf.dao;

import java.util.List;
import scf.entity.Lancamento;

public interface ILancamentoDAO {
	List<Lancamento> pesquisaPorNome(String nome) throws DAOException;

	void insert(Lancamento lanc) throws DAOException;

	void edit(Lancamento lanc, int idL) throws DAOException;

	void delete(Lancamento lanc, int idL) throws DAOException;
}