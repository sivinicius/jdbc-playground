package configuracao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;

import org.postgresql.ds.PGConnectionPoolDataSource;

public class FabricaDeConexao {

	private ConnectionPoolDataSource dataSource;

	public FabricaDeConexao() {
		PGConnectionPoolDataSource pool = new PGConnectionPoolDataSource();
		pool.setUrl("jdbc:postgresql://localhost:5432/estudo");
		pool.setUser("postgres");
		pool.setPassword("");
		dataSource = pool;
	}

	public Connection getConnection() throws SQLException {
		Connection connection = dataSource.getPooledConnection().getConnection();
		return connection;
	}

}
