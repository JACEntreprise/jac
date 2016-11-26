package controllers.action;

import controllers.routes;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class SecuredAdmin extends Security.Authenticator {


    public SecuredAdmin() {

    }

    @Override
    public String getUsername(Http.Context context) {
        final String admin = context.session().get("admin");
        if (StringUtils.isNotBlank(admin)) {
            return admin;
        } else {
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(routes.HomeController.connexionAdmin());
    }
}
