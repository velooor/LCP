package by.training.zakharchenya.courseproject.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 */
public class AdminTag extends TagSupport {

    private static final String ADMIN = "ADMIN";
    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        return ADMIN.equals(role) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

}
