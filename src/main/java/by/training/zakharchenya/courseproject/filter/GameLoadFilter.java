package by.training.zakharchenya.courseproject.filter;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;
import by.training.zakharchenya.courseproject.entity.game.MultiGame;
import by.training.zakharchenya.courseproject.logic.GameLogic;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter class, serves to upload list of multi games before opening corresponding pages.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
@WebFilter(filterName = "GameLoadFilter", urlPatterns = {"/jsp/user/waitForGame.jsp"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class GameLoadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession ses = request.getSession();

        List<MultiGame> waitingGames;
        List<MultiGame> notMyNotActive = new ArrayList();
        List<MultiGame> myNotActive = new ArrayList();
        Account account = (Account) ses.getAttribute(Constants.ACCOUNT_KEY);
        CreditBalance cb = (CreditBalance)ses.getAttribute(Constants.CREDIT_BALANCE_KEY);
        int rate = cb.getMoneyAmount();
        waitingGames = GameLogic.loadWaitingGames();
        if(waitingGames != null){
            for (MultiGame game : waitingGames) {
                if (game.getCreator().getAccountId() == account.getAccountId()) {
                    myNotActive.add(game);
                } else if(game.getRate()<=rate){
                    notMyNotActive.add(game);
                }
            }
        }

        List<MultiGame> activeGames = GameLogic.loadMyActiveGames(account.getAccountId());
        List<MultiGame> finishedGames = new ArrayList();
        List<MultiGame> notFinishedGames = new ArrayList();
        if(activeGames != null){
            for (MultiGame game : activeGames) {
                if (game.isFinished()) {
                    finishedGames.add(game);
                } else {
                    notFinishedGames.add(game);
                }
            }
        }
        ses.setAttribute(Constants.FINISHED_GAMES_KEY, finishedGames);
        ses.setAttribute(Constants.NOT_FINISHED_GAMES_KEY, notFinishedGames);
        ses.setAttribute(Constants.MY_NOT_ACTIVE_GAMES_KEY, myNotActive);
        ses.setAttribute(Constants.NOT_MY_NOT_ACTIVE_GAMES_KEY, notMyNotActive);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}