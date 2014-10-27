package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DAOFacade {
	
	private DataSource ds;
	private Connection conn;
	
	private RecipesDAO recipesDAO;
	private UsersDAO usersDAO;
	
	public DAOFacade(DataSource ds) {
		this.ds = ds;
	}
	
	public Connection getConnection() {
		if (conn == null) {
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public RecipesDAO getRecipesDAO() {
		if (recipesDAO == null) recipesDAO = new RecipesDAO(getConnection());
		return recipesDAO;
	}

	public UsersDAO getUsersDAO() {
		if (usersDAO == null) usersDAO = new UsersDAO(getConnection());
		return usersDAO;
	}

}
