package th.ac.kmitl.it.foodbook.filters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import th.ac.kmitl.it.foodbook.beans.User;
import th.ac.kmitl.it.foodbook.daos.UsersDAO;

@WebFilter({
    "/favorites/index",
    "/favorites/create",
    "/favorites/delete",
    "/rates",
    "/users/change-password",
})
public class UserAuthenticationFilter implements Filter {
    
    public UserAuthenticationFilter() {
        
    }
    
    public void destroy() {
        
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        
        if(session.getAttribute("user") != null){
            DataSource ds = (DataSource) httpRequest.getServletContext().getAttribute("ds");
            try{
                Connection conn = ds.getConnection();
                UsersDAO usersDAO = new UsersDAO(conn);
                User user = (User) session.getAttribute("user");
                User userdb = usersDAO.findByUsername(user.getUsername());
                
                
                if(userdb == null){
                    session.setAttribute("user", null);
                }
                conn.close();
                
                
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        
        if (session.getAttribute("user") == null) {
            session.setAttribute("referrer", new String(httpRequest.getRequestURL().append('?').append(httpRequest.getQueryString())));
            httpResponse.sendRedirect("/users/authenticate");
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    public void init(FilterConfig fConfig) throws ServletException {
        
    }
    
}
