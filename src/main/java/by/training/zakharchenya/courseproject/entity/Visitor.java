package by.training.zakharchenya.courseproject.entity;

/**
 * Created by Lagarde on 16.03.2017.
 */
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public class Visitor implements Serializable {
    public enum Role {
        ADMIN, USER, GUEST
    }

    private Role role;
    private Locale locale; // TODO: delete
    private String currentPage; // TODO: ??
    private Account.StatusEnum status;

    public Visitor() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public Account.StatusEnum getStatus() {
        return status;
    }

    public void setStatus(Account.StatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visitor visitor = (Visitor) o;

        if (role != visitor.role) return false;
        if (locale != null ? !locale.equals(visitor.locale) : visitor.locale != null) return false;
        if (currentPage != null ? !currentPage.equals(visitor.currentPage) : visitor.currentPage != null) return false;
        return status == visitor.status;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (currentPage != null ? currentPage.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }


}
