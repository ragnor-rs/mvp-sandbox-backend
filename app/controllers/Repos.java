package controllers;

import com.avaje.ebean.Ebean;
import models.Repo;
import models.Response;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;
import static play.mvc.Results.ok;

/**
 * Created by Reist on 16.11.15.
 */
public class Repos {

    public static Result list() {
        Response<List<Repo>> response = new Response<>();
        response.setData(Ebean.find(Repo.class).findList());
        return ok(toJson(response));
    }

    public static Result get(Long id) {
        Response<Repo> response = new Response<>();
        response.setData(Ebean.find(Repo.class, id));
        return ok(toJson(response));
    }

}
