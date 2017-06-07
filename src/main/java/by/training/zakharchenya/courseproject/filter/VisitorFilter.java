package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter class, is responsible for working with visitors.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
@WebFilter(filterName = "VisitorFilter", urlPatterns = {"/*"})
public class VisitorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        initUserState(request);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void initUserState(HttpServletRequest req) {
        Visitor visitor = (Visitor)req.getSession().getAttribute(Constants.VISITOR_KEY);
        if (visitor == null) {
            visitor = new Visitor();
            visitor.setRole(Visitor.Role.GUEST);
            visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_INDEX));
            req.getSession().setAttribute(Constants.VISITOR_KEY, visitor);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
