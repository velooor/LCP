package by.training.zakharchenya.courseproject.filter;


import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter class, serves to prevent unauthorized access to the administrator settings.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/jsp/admin/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Visitor visitor = (Visitor) request.getSession().getAttribute(Constants.VISITOR_KEY);
        if (visitor.getRole() != Visitor.Role.ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
