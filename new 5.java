////////////////////////////////////
import java.util.List;
import scf.entity.Lancamento;
public interface ILancamentoDAO {
	int insert(Lancamento lanc, int pos) throws DAOException;
	List<Lancamento> edit(int idL) throws DAOException;
	int delete (Lancamento lanc) throws DAOException;
}
////////////////////////////////////
public class DAOException extends Exception {
	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

}
////////////////////////////////////
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static ConnectionManager instance;
	private Connection connect;
	private String connectionURL = "jdbc:hsqldb:hsql://localhost/scf";
	private String user = "SA";
	private String pass = "";
	
	private ConnectionManager() { 
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			System.out.println("Driver Carregado");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ConnectionManager getInstance() { 
		if (instance == null) { 
			instance = new ConnectionManager();
		}
		return instancia;
	}
	
	public Connection getConnection() throws SQLException { 
		if (connect == null || connect.isClosed()) { 
			connect = DriverManager.getConnection(connectionURL, user, pass);
			System.out.println("Gerada uma nova conexão");
		} else {
			System.out.println("Reusando uma conexão existente");
		}
		return connect;
	}

}
////////////////////////////////////
public class ControlException extends Exception {
	public ControlException() {
		super();
	}

	public ControlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ControlException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControlException(String message) {
		super(message);
	}

	public ControlException(Throwable cause) {
		super(cause);
	}
}
////////////////////////////////////
public class UseLancamentoDAO implements ILancamentoDAO {
	
	@Override
	public int adicionar(Lancamento l, int pos) throws DAOException {
		lista.add(l);
		dataList.clear();
		dataList.addAll(lista);
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt;
			String sql;
			if (pos == -1){
				sql = "SET IDENTITY_INSERT lancamento on "
				+"INSERT INTO lancamento"
				+ "(idU,tipoL,descricaoL,dataL,valorL,idCategoria, idL) VALUES"
				+ " (?,?,?,?,?,?,?)"
				+" SET IDENTITY_INSERT lancamento off";
				stmt = con.prepareStatement(sql);
				stmt.setInt(7, pos);
			} else {
				sql = "INSERT INTO lancamento" 
				+"(idU,tipoL,descricaoL,dataL,valorL,idCategoria) VALUES"
				+" (?,?,?,?,?,?)";
				stmt = con.prepareStatement(sql);
			}
			stmt.setInt(1, l.getIdUsuario());
			stmt.setString(2, l.getTpLancamento() == 0? "Despesa" : "Renda");
			stmt.setString(3, l.getDescricao());
			Date data = new Date(l.getDtLancamento().getTime());
			stmt.setDate(4, data);
			stmt.setDouble(5, l.getValor());
			stmt.setInt(6, l.getIdCat());
			stmt.executeUpdate();
			con.close();

		} catch (SQLException e) {
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
			return pos;
		}
		return pos = -1;
	}
	
	@Override
	public List<Lancamento> edit(int idL) throws DAOException {
		List<Lancamento> lista = new ArrayList();
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			String sqlUpdate = "UPDATE lancamento "
								+"SET idU = ?, tipoL = ?, descricaoL = ?, "
								+"dataL = ?, valorL = ?, idCategoria = ?"
								+" WHERE idL = ?";
			PreparedStatement state = con.prepareStatement(sqlUpdate);
			state.setInt(1, l.getIdUsuario());
			state.setString(2, l.getTpLancamento() == 0? "Despesa" : "Renda");
			state.setString(3, l.getDescricao());
			Date data = new Date(l.getDtLancamento().getTime());
			state.setDate(4, data);
			state.setDouble(5, l.getValor());
			state.setInt(6, l.getIdCat());
			state.setInt(7, idL);
			state.executeUpdate();
			con.close;
		} catch (SQLException e){
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
		return lista;
	}
	
	public int deletar(Lancamento l, int idL) throws DAOException {
		lista.remove(l);
		System.out.println(String.format("Removendo %s da lista, tamanho: %d", l, lista.size()));
		dataList.clear();
		dataList.addAll(lista);
		try {
			Connection con = ConnectionManager.getInstance().getConnection();
			String sqlUpdate = "DELETE FROM lancamento "
								+" WHERE idL = ?";
			PreparedStatement state = con.prepareStatement(sqlUpdate);
			state.setInt(1, idL);
			state.executeUpdate();
			con.close;
		} catch (SQLException e){
			System.out.println("Erro na conexão com o banco");
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
}
////////////////////////////////////
//no home isso:
@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnBottomAdd) {
			TransacaoBoundary transc = new TransacaoBoundary();
			try {
				transc.start(this.st, this.control);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (event.getTarget() == btnBottomEditar) {
			Lancamento l = this.tableView.getSelectionModel().getSelectedItem();
			TransacaoBoundary transc = new TransacaoBoundary();
			transc.edit(st, l);
		} else if (event.getTarget() == btnBottomDeletar) {
			Lancamento l = this.tableView.getSelectionModel().getSelectedItem();
			control.deletar(l, l.getIdLancamento());
		}
	}
