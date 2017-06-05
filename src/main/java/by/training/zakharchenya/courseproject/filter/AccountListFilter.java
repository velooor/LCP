package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.logic.AdminLogic;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lagarde on 13.04.2017.
 */
@WebFilter(filterName = "AccountListFilter", urlPatterns = {"/jsp/admin/admin_settings.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class AccountListFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        List<Account> allUsers;
        List<Account> activeUsers = new ArrayList<>();
        List<Account> bannedUsers = new ArrayList<>();
        Account account = (Account) request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        allUsers = AdminLogic.loadAllUsers();
        List<Integer> params = AdminLogic.loadParams();
        request.getSession().setAttribute(Constants.MIN_POINTS_KEY, params.get(0));
        request.getSession().setAttribute(Constants.MIN_RATE_KEY, params.get(1));
        request.getSession().setAttribute(Constants.USERS_LIST_KEY, allUsers);
        for (Account acc : allUsers) {
            if (acc.getStatus()== Account.StatusEnum.ACTIVE) {
                activeUsers.add(acc);
            } else {
                bannedUsers.add(acc);
            }
        }
        request.getSession().setAttribute(Constants.USERS_LIST_KEY, allUsers);
        request.getSession().setAttribute(Constants.ACTIVE_USERS_KEY, activeUsers);
        request.getSession().setAttribute(Constants.BANNED_USERS_KEY, bannedUsers);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}