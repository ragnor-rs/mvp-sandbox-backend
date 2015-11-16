package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Created by Reist on 16.11.15.
 */
@Entity
public class Repo extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @JsonProperty("html_url")
    private String url;

    @ManyToOne
    @Column(nullable = false)
    private User owner;

    @JsonProperty("like_count")
    private long likeCount;

    @JsonProperty("liked_by_me")
    @Transient
    private boolean likedByMe;

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

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLikedByMe() {
        return likedByMe;
    }

    public void setLikedByMe(boolean likedByMe) {
        this.likedByMe = likedByMe;
    }

}
