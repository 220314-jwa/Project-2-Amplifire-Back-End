package Utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//a factory is responsible for generating an object and returning it
//singleton is a design pattern where we only create one instance of a class, saves memory so we're not re-instantiating it every time

public class ConnectionFactory {
	// private - only this class can directly access
	// static - it belongs to the class, rather than a specific instance (singleton
	// design pattern)
	private static ConnectionFactory connectionFactory = null;
	private static Properties properties;

	private ConnectionFactory() {
		InputStream stream = ConnectionFactory.class.getClassLoader().getResourceAsStream("dbConfig.properties");
		try {
			properties = new Properties();
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionFactory getConnectionFactory() {
		if (connectionFactory == null)
			connectionFactory = new ConnectionFactory();
		return (ConnectionFactory) connectionFactory;
	}

	// return our connection to the database:
	public Connection getConnection() {
		Connection connection = null;
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");

		// try connecting to the database:
		try {
			// get connection
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// if something goes wrong, view the stack trace
			e.printStackTrace();
		}
		return connection;
	}

}
