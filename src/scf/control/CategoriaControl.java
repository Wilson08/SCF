package scf.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scf.dao.DAOException;
import scf.dao.UseCategoriaDAO;
import scf.entity.Categoria;

public class CategoriaControl  {
	//private List<Categoria> lista = new ArrayList<>();
	private UseCategoriaDAO categoriaDAO = new UseCategoriaDAO();
	private ObservableList<Categoria> dataList = FXCollections.observableArrayList();

	public CategoriaControl() {

	}

	public ObservableList<Categoria> getDataList() {
		return dataList;
	}

	public void setDataList(ObservableList<Categoria> dataList) {
		this.dataList = dataList;
	}

	public void adicionar(Categoria c) throws ControlException  {
		try {
			categoriaDAO.insert(c);
			dataList.clear();
			dataList.addAll(categoriaDAO.pesquisar(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}
	
	public void pesquisar(String nome) throws ControlException { 
		try {
			dataList.clear();
			dataList.addAll(categoriaDAO.pesquisar(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}

	public void deletar(Categoria c) throws ControlException {
		//lista.remove(l);
		//System.out.println(String.format("Removendo %s da lista, tamanho: %d", l, lista.size()));
		dataList.clear();
		try {
			categoriaDAO.delete(c, c.getIdCat());
			dataList.clear();
			dataList.addAll(categoriaDAO.pesquisar(""));
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ControlException(e);
		}
	}
}
