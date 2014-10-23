package th.ac.kmitl.it.foodbook.listeners;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.Foodbook;

@WebListener
public class InitializeListener implements ServletContextListener {

	@Resource(name = "jdbc/foodbook_development")
	private DataSource ds;

	public InitializeListener() {

	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		
		context.setAttribute("ds", ds);
		context.setAttribute("siteTitle", Foodbook.NANE);
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}