package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Reist on 16.11.15.
 */
@Entity
public class Repo extends AbstractEntity {

    private String name;

    private String url;

    @ManyToOne
    private User owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
