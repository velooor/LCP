package by.training.zakharchenya.courseproject.servlet;


import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.action.CommandFactory;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import static by.training.zakharchenya.courseproject.servlet.Constants.PAGE_ERROR;
import static by.training.zakharchenya.courseproject.servlet.Constants.STATE_KEY;

@WebServlet(name = "WebServlet", urlPatterns = {"/main"})
@MultipartConfig(maxFileSize = 52428800, maxRequestSize = 52428800)
public class Controller extends HttpServlet {

    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().closePool();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandFactory clientCommand = new CommandFactory();
        Command command = clientCommand.getCurrentCommand(request);
        String result = command.execute(request);
        if (result != null) {
            if(request.getSession().getAttribute(STATE_KEY) == Constants.State.RESPONSE){
                response.getOutputStream().write(Base64.getDecoder().decode(result));
            }else if(request.getSession().getAttribute(STATE_KEY) == Constants.State.AJAX){
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                    response.getWriter().write(result);
            }
            else{
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(result);
                dispatcher.forward(request, response);
            }
        } else {
            result = ConfigurationManager.getProperty(PAGE_ERROR);
            response.sendRedirect(request.getContextPath() + result);
        }

    }
}