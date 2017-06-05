package by.training.zakharchenya.courseproject.action.command;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LocaleCommand implements Command {
    private static final String LOCALE_ATTRIBUTE="language";
    private static final String LOCALE_ATTR="locale";

    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter(LOCALE_ATTRIBUTE);
        HttpSession session = request.getSession();
        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
        session.setAttribute(LOCALE_ATTR, locale);
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        return request.getContextPath() + visitor.getCurrentPage();
    }
}
