package scf.entity;

import java.util.Date;

public class Lancamento {
	private int idLancamento;
	private int tpLancamento;
    private double valor;
    private String descricao;
    private Date dtLancamento;
    private int idUsuario;
    private int idCat;
    
    public int getIdLancamento() {
		return idLancamento;
	}
	public void setIdLancamento(int idLancamento) {
		this.idLancamento = idLancamento;
	}
	public int getTpLancamento() {
		return tpLancamento;
	}
	public void setTpLancamento(int tpLancamento) {
		this.tpLancamento = tpLancamento;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDtLancamento() {
		return dtLancamento;
	}
	public void setDtLancamento(Date dtLancamento) {
		this.dtLancamento = dtLancamento;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdCat() {
		return idCat;
	}
	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}
	public Date getFabricacao() {
		// TODO Auto-generated method stub
		return null;
	}

}
