package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Like;
import models.Repo;
import models.Response;
import models.User;
import org.jetbrains.annotations.NotNull;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;
import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by Reist on 16.11.15.
 */
public class Repos {

    public static Result list() {
        User user = currentUser();
        if (user == null) {
            return prepareError("User is null");
        }
        List<Repo> list = Ebean.find(Repo.class).findList();
        return prepareSuccess(list, user);
    }

    public static Result get(Long id) {
        User user = currentUser();
        if (user == null) {
            return prepareError("User is null");
        }
        Repo repo = Ebean.find(Repo.class, id);
        return prepareSuccess(repo, user);
    }

    public static Result like(Long id) {
        User user = currentUser();
        if (user == null) {
            return prepareError("User is null");
        }
        Repo repo = Ebean.find(Repo.class, id);
        if (like(repo, user) == null) {
            Like like = new Like();
            like.setRepo(repo);
            like.setUser(user);
            Ebean.save(like);
            return ok();
        } else {
            return prepareError("Like is not null");
        }
    }

    public static Result unlike(Long id) {
        User user = currentUser();
        if (user == null) {
            return prepareError("User is null");
        }
        Ebean.createSqlUpdate("delete from " + Like.TABLE_NAME + " where " + Like.COLUMN_REPO_ID + " = :repo_id_value and " + Like.COLUMN_USER_ID + " = :user_id_value")
                .setParameter("repo_id_value", id)
                .setParameter("user_id_value", user.getId())
                .execute();
        return ok();
    }

    public static Result create() {
        User user = currentUser();
        if (user == null) {
            return prepareError("User is null");
        }
        JsonNode json = request().body().asJson();
        if (json == null) {
            return prepareError("Repo is null");
        }
        Repo repo = Json.fromJson(json, Repo.class);
        repo.setOwner(user);
        Ebean.save(repo);
        Response<Repo> response = new Response<>();
        response.setData(repo);
        return ok(toJson(response));
    }

    @NotNull
    private static Result prepareError(String error) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("error", error);
        return badRequest(toJson(node));
    }

    @NotNull
    private static Result prepareSuccess(Repo repo, User user) {
        Response<Repo> response = new Response<>();
        repo.setLikedByMe(like(repo, user) != null);
        response.setData(repo);
        return ok(toJson(response));
    }

    @NotNull
    public static Result prepareSuccess(List<Repo> list, User user) {
        Response<List<Repo>> response = new Response<>();
        for (Repo repo : list) {
            repo.setLikedByMe(like(repo, user) != null);
        }
        response.setData(list);
        return ok(toJson(response));
    }

    private static Like like(Repo repo, User user) {
        return Ebean.find(Like.class).where(Expr.and(Expr.eq("user", user), Expr.eq("repo", repo))).findUnique();
    }

    private static User currentUser() {
        try {
            return Ebean.find(User.class, Long.parseLong(request().getQueryString("user_id")));
        } catch (Exception e) {
            return null;
        }
    }

}
