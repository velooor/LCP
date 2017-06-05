package by.training.zakharchenya.courseproject.action;

import javax.servlet.http.HttpServletRequest;

/** Interface, that sets behaviour for all commands
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public interface Command {
    /**@param request from client
     * @return path for jsp file / image, encoded to string / xml file as string
     */
    String execute(HttpServletRequest request);
}

