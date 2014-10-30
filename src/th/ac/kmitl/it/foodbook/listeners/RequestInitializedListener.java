package th.ac.kmitl.it.foodbook.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import th.ac.kmitl.it.foodbook.utils.Alert;

@WebListener
public class RequestInitializedListener implements ServletRequestListener {

    public RequestInitializedListener() {

    }

    public void requestInitialized(ServletRequestEvent sre)  {
    	
    }

    public void requestDestroyed(ServletRequestEvent sre)  { 
    	HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
    	HttpSession session = request.getSession();
    	Alert alert = (Alert) session.getAttribute("alert");
    	if (alert != null && alert.isShowed()) session.setAttribute("alert", null); 
    }
	
}
