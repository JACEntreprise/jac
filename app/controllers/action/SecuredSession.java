package controllers.action;

import org.apache.commons.lang3.StringUtils;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class SecuredSession extends Security.Authenticator {


    public SecuredSession() {

    }

    @Override
    public String getUsername(Http.Context context) {
        final String connect = context.session().get("connect");
        if (StringUtils.isNotBlank(connect)) {
            return connect;
        } else {
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect("/");
    }
}
