package th.ac.kmitl.it.foodbook.listeners;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ContextInitializedListener implements ServletContextListener {
    
    @Resource(name = "jdbc/foodbook_development")
    private DataSource ds;

	public ContextInitializedListener() {

	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		
		context.setAttribute("siteTitle", "Foodbook");
		context.setAttribute("ds", ds);
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}
