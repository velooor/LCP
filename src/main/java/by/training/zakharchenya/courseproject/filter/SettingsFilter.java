package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.AdminLogic;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter class, serves to upload credit card and account information before opening corresponding pages.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
@WebFilter(filterName = "SettingsFilter", urlPatterns = {"/jsp/user/profile_settings.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class SettingsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession ses = request.getSession();

        Account account = (Account) request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        List<String> cardInfo;
        try{
            cardInfo = MoneyInfoLogic.showCreditCardInfo(account.getAccountId());
            if(cardInfo != null){
                ses.setAttribute(Constants.CARD_FIRST_KEY, cardInfo.get(0).substring(0,4));
                ses.setAttribute(Constants.CARD_SECOND_KEY, cardInfo.get(0).substring(4,8));
                ses.setAttribute(Constants.CARD_THIRD_KEY, cardInfo.get(0).substring(8,12));
                ses.setAttribute(Constants.CARD_FOURTH_KEY, cardInfo.get(0).substring(12));
                ses.setAttribute(Constants.CARD_MONTH_KEY, cardInfo.get(1));
                ses.setAttribute(Constants.CARD_YEAR_KEY, cardInfo.get(2));
            }else{
                ses.setAttribute(Constants.CARD_FIRST_KEY, "");
                ses.setAttribute(Constants.CARD_SECOND_KEY, "");
                ses.setAttribute(Constants.CARD_THIRD_KEY, "");
                ses.setAttribute(Constants.CARD_FOURTH_KEY, "");
                ses.setAttribute(Constants.CARD_MONTH_KEY, "");
                ses.setAttribute(Constants.CARD_YEAR_KEY, "");
            }
        } catch (LogicException e){   }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}