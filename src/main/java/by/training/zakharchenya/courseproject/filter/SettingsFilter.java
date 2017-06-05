package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.AdminLogic;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "SettingsFilter", urlPatterns = {"/jsp/user/profile_settings.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class SettingsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        Account account = (Account) request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        List<String> cardInfo;
        try{
            cardInfo = MoneyInfoLogic.showCreditCardInfo(account.getAccountId());
            if(cardInfo != null){
                request.getSession().setAttribute(Constants.CARD_FIRST_KEY, cardInfo.get(0).substring(0,4));
                request.getSession().setAttribute(Constants.CARD_SECOND_KEY, cardInfo.get(0).substring(4,8));
                request.getSession().setAttribute(Constants.CARD_THIRD_KEY, cardInfo.get(0).substring(8,12));
                request.getSession().setAttribute(Constants.CARD_FOURTH_KEY, cardInfo.get(0).substring(12));
                request.getSession().setAttribute(Constants.CARD_MONTH_KEY, cardInfo.get(1));
                request.getSession().setAttribute(Constants.CARD_YEAR_KEY, cardInfo.get(2));
            }else{
                request.getSession().setAttribute(Constants.CARD_FIRST_KEY, null);
                request.getSession().setAttribute(Constants.CARD_SECOND_KEY, null);
                request.getSession().setAttribute(Constants.CARD_THIRD_KEY, null);
                request.getSession().setAttribute(Constants.CARD_FOURTH_KEY, null);
                request.getSession().setAttribute(Constants.CARD_MONTH_KEY, null);
                request.getSession().setAttribute(Constants.CARD_YEAR_KEY, null);
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