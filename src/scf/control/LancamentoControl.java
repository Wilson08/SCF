package scf.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scf.dao.DAOException;
import scf.dao.UseLancamentoDAO;
import scf.entity.Lancamento;

public class LancamentoControl {
	private UseLancamentoDAO lancamentoDAO = new UseLancamentoDAO();
	private ObservableList<Lancamento> dataList = FXCollections.observableArrayList();

	public LancamentoControl() {

	}

	public ObservableList<Lancamento> getDataList() {
		return dataList;
	}

	public void setDataList(ObservableList<Lancamento> dataList) {
		this.dataList = dataList;
	}

	public void adicionar(Lancamento l) throws ControlException {
		try {
			lancamentoDAO.insert(l);
			dataList.clear();
			dataList.addAll(lancamentoDAO.pesquisaPorNome(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}

	public void pesquisar(String nome) throws ControlException {
		try {
			dataList.clear();
			dataList.addAll(lancamentoDAO.pesquisaPorNome(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}

	public void deletar(Lancamento l) throws ControlException {
		// System.out.println(String.format("Removendo %s da lista, tamanho: %d", l,
		// lista.size()));
		dataList.clear();
		try {
			lancamentoDAO.delete(l, l.getIdLancamento());
			dataList.clear();
			dataList.addAll(lancamentoDAO.pesquisaPorNome(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}
}
