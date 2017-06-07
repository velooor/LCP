package by.training.zakharchenya.courseproject.entity;

import java.time.LocalDateTime;

/** Entity class, serves for processing relative object Message from database.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class Message {
    private int messageId;
    private Account creator;
    private Account addresse;
    private boolean read;
    private LocalDateTime creationTime;
    private String message; // TODO: long text
    private String theme;

    public Message(Account creator, boolean read, LocalDateTime creationTime, String message, String theme, int messageId) {
        this.creator = creator;
        this.read = read;
        this.creationTime = creationTime;
        this.message = message;
        this.theme = theme;
        this.messageId=messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public Account getAddresse() {
        return addresse;
    }

    public void setAddresse(Account addresse) {
        this.addresse = addresse;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (messageId != message1.messageId) return false;
        if (read != message1.read) return false;
        if (creator != null ? !creator.equals(message1.creator) : message1.creator != null) return false;
        if (addresse != null ? !addresse.equals(message1.addresse) : message1.addresse != null) return false;
        if (creationTime != null ? !creationTime.equals(message1.creationTime) : message1.creationTime != null)
            return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        return theme != null ? theme.equals(message1.theme) : message1.theme == null;
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (addresse != null ? addresse.hashCode() : 0);
        result = 31 * result + (read ? 1 : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        return result;
    }
}
