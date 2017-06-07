package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.servlet.Constants;
import by.training.zakharchenya.courseproject.util.URIAnalyzer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter class, serves to work and process user current page.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
@WebFilter(filterName = "CurrentPageFilter",  urlPatterns = {"/jsp/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class CurrentPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
        String uri = URIAnalyzer.cleanURI(request.getRequestURI().substring(request.getContextPath().length()));
        if (URIAnalyzer.isPageURI(uri)) {
            visitor.setCurrentPage(uri);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
