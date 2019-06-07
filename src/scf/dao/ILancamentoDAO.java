package scf.dao;

import java.util.List;
import scf.entity.Lancamento;

public interface ILancamentoDAO {
	void insert(Lancamento lanc) throws DAOException;

	List<Lancamento> edit(Lancamento lanc, int idL) throws DAOException;

	void delete(Lancamento lanc, int idL) throws DAOException;
}