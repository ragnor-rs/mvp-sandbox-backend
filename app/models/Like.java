package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Reist on 16.11.15.
 */
@Entity
@Table(name = Like.TABLE_NAME)
public class Like {

    public static final String TABLE_NAME = "repo_like";

    @ManyToOne
    private User user;

    @ManyToOne
    private Repo repo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

}
