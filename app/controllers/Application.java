package controllers;

import akka.actor.ActorSystem;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Like;
import models.Repo;
import org.jetbrains.annotations.NotNull;
import play.libs.Akka;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import views.html.index;

import java.util.concurrent.TimeUnit;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    static {
        ActorSystem system = Akka.system();
        system.scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS),   // initial delay
                Duration.create(1, TimeUnit.MINUTES),        // job period
                () -> {
                    Ebean.find(Repo.class).findEach(repo -> {
                        long likeCount = Ebean.find(Like.class).where(Expr.eq("repo", repo)).findRowCount();
                        repo.setLikeCount(likeCount);
                        Ebean.save(repo);
                    });
                },
                system.dispatcher()
        );
    }

    @NotNull
    public static Result prepareError(String error) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("error", error);
        return badRequest(toJson(node));
    }

}
