package models;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Reist on 16.11.15.
 */
@Entity
@Table(name = User.TABLE_NAME)
public class User extends AbstractEntity {

    public static final String TABLE_NAME = "repo_user";

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
