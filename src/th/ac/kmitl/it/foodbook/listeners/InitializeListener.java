package th.ac.kmitl.it.foodbook.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import th.ac.kmitl.it.foodbook.Foodbook;

@WebListener
public class InitializeListener implements ServletContextListener {

	public InitializeListener() {

	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		
		context.setAttribute("siteTitle", Foodbook.NANE);
		
		context.setAttribute("ds", Foodbook.dataSource);
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}
