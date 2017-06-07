package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.logic.AdminLogic;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Filter class, serves to prevent unauthorized access to the system.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {"/jsp/user/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Visitor visitor = (Visitor) request.getSession().getAttribute(Constants.VISITOR_KEY);
        if (visitor.getRole() == Visitor.Role.GUEST || visitor.getStatus() == Account.StatusEnum.BANNED) {
            visitor.setRole(Visitor.Role.GUEST);
            request.getSession().setAttribute(Constants.ACCOUNT_KEY, null);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            List<Integer> params = AdminLogic.loadParams();
            request.getSession().setAttribute(Constants.MIN_POINTS_KEY, params.get(0));
            request.getSession().setAttribute(Constants.MIN_RATE_KEY, params.get(1));
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
