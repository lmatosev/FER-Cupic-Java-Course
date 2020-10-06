package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * 
 * Initializes the database and creates tables Polls and PollOptions if they
 * don't exist yet.
 * 
 * @author Lovro Matošević
 *
 */

@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 * 
	 * Initializes the connection pool and creates and populates the needed tables.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties properties = new Properties();

		String configFileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");

		String host = null;
		String port = null;
		String name = null;
		String user = null;
		String password = null;

		try {
			properties.load(new FileInputStream(Paths.get(configFileName).toFile()));
			host = properties.getProperty("host");
			port = properties.getProperty("port");
			name = properties.getProperty("name");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch (Exception e) {
			throw new RuntimeException("Error while reading properties file.");
		}

		StringBuilder sb = new StringBuilder();

		sb.append("jdbc:derby://").append(host).append(":").append(port).append("/").append(name).append(";user=")
				.append(user).append(";password=").append(password).append(";create=true");

		String connectionURL = sb.toString();

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error while initializing pool.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		Connection connection = null;
		try {
			connection = cpds.getConnection(user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		String polls = "CREATE TABLE Polls\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
				+ " title VARCHAR(150) NOT NULL,\n" + " message CLOB(2048) NOT NULL\n" + ")";

		String pollOptions = "CREATE TABLE PollOptions\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
				+ " optionTitle VARCHAR(100) NOT NULL,\n" + " optionLink VARCHAR(150) NOT NULL,\n" + " pollID BIGINT,\n"
				+ " votesCount BIGINT,\n" + " FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + ")";

		this.createTable(connection, polls);
		this.createTable(connection, pollOptions);

		this.populatePolls(connection, sce);

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

	}

	/**
	 * Populates the PollOptions table.
	 * 
	 * @param connection         - connection used for populating the table
	 * @param sce                - context used for getting the path to the voting
	 *                           definition file
	 * @param definitionFileName - name of the file containing voting definitions
	 * @param pollID             - id of the parent poll
	 */
	private void populatePollOptions(Connection connection, ServletContextEvent sce, String definitionFileName,
			int pollID) {

		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement("select count(*) as rowcnt from PollOptions");
			ResultSet rs = pst.executeQuery();
			rs.next();

			String fileName = sce.getServletContext().getRealPath("/WEB-INF/" + definitionFileName);

			BufferedReader reader;

			try {

				reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

				String line;

				while ((line = reader.readLine()) != null) {

					String[] lineSplit = line.split("\t");

					pst = connection.prepareStatement(
							"INSERT INTO PollOptions (optionTitle, optionLink, pollID,votesCount) values (?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);

					pst.setString(1, lineSplit[1]);
					pst.setString(2, lineSplit[2]);
					pst.setInt(3, pollID);
					pst.setInt(4, 0);
					pst.executeUpdate();

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Populates the polls if they are empty.
	 * 
	 * @param connection - connection used for inserting into the table
	 * @param sce
	 */
	private void populatePolls(Connection connection, ServletContextEvent sce) {

		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement("select count(*) as rowcnt from Polls");
			ResultSet rs = pst.executeQuery();
			rs.next();
			if (rs.getInt("rowcnt") < 2) {

				pst = connection.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
						Statement.RETURN_GENERATED_KEYS);

				pst.setString(1, "Glasanje za omiljeni bend:");
				pst.setString(2,
						"Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");

				pst.executeUpdate();

				ResultSet keys = pst.getGeneratedKeys();

				keys.next();
				int bandKey = keys.getInt(1);

				pst = connection.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
						Statement.RETURN_GENERATED_KEYS);

				pst.setString(1, "Glasanje za omiljenog pjevača:");
				pst.setString(2,
						"Od sljedećih pjevača, koji Vam je pjevač najdraži? Kliknite na link kako biste glasali!");

				pst.executeUpdate();

				keys = pst.getGeneratedKeys();
				keys.next();
				int singerKey = keys.getInt(1);

				this.populatePollOptions(connection, sce, "bendovi-glasanje-definicija.txt", bandKey);
				this.populatePollOptions(connection, sce, "pjevaci-glasanje-definicija.txt", singerKey);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Creates a table if no table exists.
	 * 
	 * @param connection - connection used for creating the table
	 * @param statement  - statement which should be used
	 */
	private void createTable(Connection connection, String statement) {

		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(statement, Statement.NO_GENERATED_KEYS);
			pst.execute();
		} catch (SQLException e) {
			if (e.getSQLState().equals("X0Y32")) {
				return;
			}
			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * Destroys the pool which was created.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}