package by.training.zakharchenya.courseproject.entity;

import java.util.Arrays;
import java.sql.Date;

/** Entity class, serves for processing relative object Account from database.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class Account {
    public enum StatusEnum {
        ACTIVE, BANNED
    }

    private int accountId;
    private String login;
    private String email;
    private String name;
    private String surname;
    private boolean admin;
    private StatusEnum status;
    private Date birthDate;
    private byte[] avatar;
    private int numOfVictories;
    private int numOfGames;

    public Account() {
    }

    public Account(int accountId, String login, String email, String name, String surname, boolean admin, StatusEnum status, Date birthDate, byte[] avatar, int numOfVictories, int numOfGames) {
        this.accountId = accountId;
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.admin = admin;
        this.status = status;
        this.birthDate = birthDate;
        this.avatar = avatar;
        this.numOfVictories = numOfVictories;
        this.numOfGames = numOfGames;
    }
    public Account(String name, String surname, String login, boolean admin) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.admin = admin;
    }
    public Account(int accountId, String name, String surname, String login) {
        this.accountId = accountId;
        this.login = login;
        this.name = name;
        this.surname = surname;
    }

    public Account(String name, String surname, String login) {
        this.login = login;
        this.name = name;
        this.surname = surname;
    }
    public Account(int accountId) {
        this.accountId = accountId;
    }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getNumOfVictories() {
        return numOfVictories;
    }

    public void setNumOfVictories(int numOfVictories) {
        this.numOfVictories = numOfVictories;
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountId != account.accountId) return false;
        if (admin != account.admin) return false;
        if (numOfVictories != account.numOfVictories) return false;
        if (numOfGames != account.numOfGames) return false;
        if (login != null ? !login.equals(account.login) : account.login != null) return false;
        if (email != null ? !email.equals(account.email) : account.email != null) return false;
        if (name != null ? !name.equals(account.name) : account.name != null) return false;
        if (surname != null ? !surname.equals(account.surname) : account.surname != null) return false;
        if (status != account.status) return false;
        if (birthDate != null ? !birthDate.equals(account.birthDate) : account.birthDate != null) return false;
        return Arrays.equals(avatar, account.avatar);
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(avatar);
        result = 31 * result + numOfVictories;
        result = 31 * result + numOfGames;
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", admin=" + admin +
                ", status=" + status +
                ", birthDate=" + birthDate +
                ", avatar=" + Arrays.toString(avatar) +
                ", numOfVictories=" + numOfVictories +
                ", numOfGames=" + numOfGames +
                '}';
    }



}