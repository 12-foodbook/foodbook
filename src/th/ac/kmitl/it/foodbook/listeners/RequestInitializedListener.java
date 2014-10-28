package th.ac.kmitl.it.foodbook.listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestInitializedListener implements ServletRequestListener {

    public RequestInitializedListener() {

    }

    public void requestInitialized(ServletRequestEvent sre)  {
    	
    }

    public void requestDestroyed(ServletRequestEvent sre)  { 
    	
    }
	
}
