package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;

public abstract class DAO {
    
    protected Connection conn;

	public DAO(Connection conn) {
		this.conn = conn;
	}

}
