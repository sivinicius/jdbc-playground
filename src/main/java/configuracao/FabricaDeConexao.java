package configuracao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

public class FabricaDeConexao {

	private ConnectionPoolDataSource dataSource;

	public FabricaDeConexao() {
		MysqlConnectionPoolDataSource pool = new MysqlConnectionPoolDataSource();
		pool.setUrl("jdbc:mysql://localhost:3306/jdbc_playground?useTimezone=true&serverTimezone=UTC");
		pool.setUser("root");
		pool.setPassword("root");
		dataSource = pool;
	}

	public Connection obterConexao() throws SQLException {
		return dataSource.getPooledConnection().getConnection();
	}

}
