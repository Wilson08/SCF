package scf.control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scf.entity.Lancamento;

public class LancamentoControl {
	private ObservableList<Lancamento> dataList = FXCollections.observableArrayList();

	public ObservableList<Lancamento> getDataList() {
		// TODO Auto-generated method stub
		return dataList;
	}

}
