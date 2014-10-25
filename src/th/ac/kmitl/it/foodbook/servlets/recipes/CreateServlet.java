package th.ac.kmitl.it.foodbook.servlets.recipes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.Recipe;
import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.RecipesDAO;

@WebServlet("/recipes/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
    	String videoUrl = request.getParameter("video_url");
    	
    	DataSource ds = (DataSource) request.getServletContext().getAttribute("ds");
		
		boolean isSuccess = false;
    	
    	try {
			Connection conn = ds.getConnection();
			RecipesDAO recipesDAO = new RecipesDAO(conn);
			
			Recipe recipe = new Recipe();
			
			recipe.setName(name);
			recipe.setVideo_url(videoUrl);
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			recipe.setUser_id(user.getUser_id());
			
			isSuccess = recipesDAO.create(recipe);
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
			response.sendError(500);
		}
		
		HttpSession session = request.getSession();
    	
    	if (isSuccess) {
    		session.setAttribute("alert", "เพิ่มรายการอาหารเรียบร้อยแล้ว");
    	} else {
    		session.setAttribute("alert", "ไม่สามารถเพิ่มรายการอาหารได้");
    	}
    }

}
