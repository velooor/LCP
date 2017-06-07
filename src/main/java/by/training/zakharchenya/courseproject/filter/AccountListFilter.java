package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.logic.AdminLogic;
import by.training.zakharchenya.courseproject.servlet.Constants;
import com.mysql.cj.api.Session;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Filter class, serves to upload list of users and admin params before opening corresponding pages.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
@WebFilter(filterName = "AccountListFilter", urlPatterns = {"/jsp/admin/admin_settings.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class AccountListFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        List<Account> allUsers;
        List<Account> activeUsers = new ArrayList<>();
        List<Account> bannedUsers = new ArrayList<>();
        allUsers = AdminLogic.loadAllUsers();
        List<Integer> params = AdminLogic.loadParams();
        HttpSession ses = request.getSession();
        ses.setAttribute(Constants.MIN_POINTS_KEY, params.get(0));
        ses.setAttribute(Constants.MIN_RATE_KEY, params.get(1));
        ses.setAttribute(Constants.USERS_LIST_KEY, allUsers);
        for (Account acc : allUsers) {
            if (acc.getStatus()== Account.StatusEnum.ACTIVE) {
                activeUsers.add(acc);
            } else {
                bannedUsers.add(acc);
            }
        }
        ses.setAttribute(Constants.USERS_LIST_KEY, allUsers);
        ses.setAttribute(Constants.ACTIVE_USERS_KEY, activeUsers);
        ses.setAttribute(Constants.BANNED_USERS_KEY, bannedUsers);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}