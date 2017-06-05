package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Message;
import by.training.zakharchenya.courseproject.logic.MailLogic;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebFilter(filterName = "MessageLoadFilter", urlPatterns = {"/jsp/user/messages.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class MessageLoadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        List<Message> allMessages;
        List<Message> readMessages = new ArrayList<>();
        List<Message> unreadMessages = new ArrayList<>();
        Account account = (Account) request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        allMessages = MailLogic.loadAllUserIncomingMessages(account);
        Collections.reverse(allMessages);
        for (Message mess : allMessages) {
            if (mess.isRead()) {
                readMessages.add(mess);
            } else {
                unreadMessages.add(mess);
            }
        }
        request.getSession().setAttribute(Constants.READ_MESSAGES_KEY, readMessages);
        request.getSession().setAttribute(Constants.UNREAD_MESSAGES_KEY, unreadMessages);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}