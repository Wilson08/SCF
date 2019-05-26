package scf.control;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import scf.entity.Categoria;

public class CategoriaControl {
	List<Categoria> categoria = new ArrayList<>();

    public void insert(Categoria c) {
    	if (c.getNomeCat().trim().isEmpty()) {
            msgError("Nome vazio");
            return;
        }
    	if (c.getIdCat() == 0) {
            msgError("ID Vazio");
            return;
        }
        this.categoria.add(c);
    }

    private void msgError(String corpo) {
        JOptionPane.showMessageDialog(null, corpo, "ERROR", JOptionPane.ERROR_MESSAGE);
}
	
}
