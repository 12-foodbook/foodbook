package th.ac.kmitl.it.foodbook.daos;

import javax.sql.DataSource;

public abstract class DAO {
    
    protected DataSource ds;
    
    public DAO(DataSource ds) {
    	this.ds = ds;
    }

}
