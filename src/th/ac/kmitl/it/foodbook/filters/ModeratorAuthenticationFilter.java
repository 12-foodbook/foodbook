package th.ac.kmitl.it.foodbook.filters;

import java.io.IOException;

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

@WebFilter({
    "/recipes/index",
    "/recipes/categories/index",
    "/recipes/categories/create",
    "/recipes/categories/edit",
    "/recipes/categories/delete",
    "/ingredients/index",
    "/ingredients/create",
    "/ingredients/edit",
    "/ingredients/delete",
    "/ingredients/categories/create",
    "/ingredients/categories/edit",
    "/ingredients/categories/delete",
    "/kitchenwares/index",
    "/kitchenwares/create",
    "/kitchenwares/edit",
    "/kitchenwares/delete",
    "/users/index",
    "/users/delete",
    "/moderators/create",
    "/moderators/delete"
})
public class ModeratorAuthenticationFilter implements Filter {
    
    public ModeratorAuthenticationFilter() {
        
    }
    
    public void destroy() {
        
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        if (session.getAttribute("moderator") == null) {
            session.setAttribute("referrer", new String(httpRequest.getRequestURL().append('?').append(httpRequest.getQueryString())));
            httpResponse.sendRedirect("/moderators/authenticate");
        } else {
            chain.doFilter(request, response);
        }
    }
    
    public void init(FilterConfig fConfig) throws ServletException {
        
    }
    
}
