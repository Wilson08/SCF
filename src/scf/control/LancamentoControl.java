package scf.control;


import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scf.entity.Lancamento;

public class LancamentoControl {
	private List<Lancamento> lista = new ArrayList<>();
	private ObservableList<Lancamento> dataList = FXCollections.observableArrayList();

	public ObservableList<Lancamento> getDataList() {
		// TODO Auto-generated method stub
		return dataList;
	}
	
	public void setDataList(ObservableList<Lancamento> dataList) {
		this.dataList = dataList;
	}
	
	public void adicionar(Lancamento l) {
		lista.add(l);
		System.out.println(
				String.format("Adicionado lancamento %s na lista, tamanho: %d ", 
						l, lista.size()));
		dataList.clear();
		dataList.addAll(lista);
	}
}
