package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import models.Repo;
import models.Response;
import models.User;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;
import static play.mvc.Results.ok;

/**
 * Created by Reist on 16.11.15.
 */
public class Users {

    public static Result list() {
        Response<List<User>> response = new Response<>();
        response.setData(Ebean.find(User.class).findList());
        return ok(toJson(response));
    }

    public static Result listUserRepos(Long id) {
        User user = Ebean.find(User.class, id);
        List<Repo> list = Ebean.find(Repo.class).where(Expr.eq("owner", user)).findList();
        return Repos.prepareResponse(list, user);
    }

}
