package controllers;

import akka.actor.ActorSystem;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import models.Like;
import models.Repo;
import play.libs.Akka;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import views.html.index;

import java.util.concurrent.TimeUnit;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    static {
        ActorSystem system = Akka.system();
        system.scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS),   // initial delay
                Duration.create(10, TimeUnit.SECONDS),        // run job every 5 minutes
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

}
