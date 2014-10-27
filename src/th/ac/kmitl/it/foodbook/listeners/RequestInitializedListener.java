package th.ac.kmitl.it.foodbook.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.daos.DAOFacade;

@WebListener
public class RequestInitializedListener implements ServletRequestListener {

    public RequestInitializedListener() {

    }

    public void requestInitialized(ServletRequestEvent sre)  {
    	HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
		DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");

		DAOFacade daos = new DAOFacade(ds);
		request.setAttribute("daos", daos);
    }

    public void requestDestroyed(ServletRequestEvent sre)  { 
    	HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
    	DAOFacade daos = (DAOFacade) request.getAttribute("daos");
    	daos.close();
    }
	
}
