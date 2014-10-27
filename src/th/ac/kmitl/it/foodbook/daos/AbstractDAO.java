package th.ac.kmitl.it.foodbook.daos;

import java.sql.Connection;

public abstract class AbstractDAO {
    
    protected Connection conn;

	public AbstractDAO(Connection conn) {
		this.conn = conn;
	}

}
