package controllers.action;

import controllers.routes;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {


    public Secured() {

    }

    @Override
    public String getUsername(Http.Context context) {
        final String email = context.session().get("email");
        if (StringUtils.isNotBlank(email)) {
            final String role=context.session().get("user");
            if (StringUtils.isNotBlank(role)) {
                return email;
            }
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        final String email = context.session().get("email");
        if (StringUtils.isNotBlank(email)) {
            return redirect(routes.AdminController.accueil());
        }
        return redirect(routes.HomeController.index());
    }
}
