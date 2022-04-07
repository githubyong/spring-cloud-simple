package org.example.scs.common;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OauthOnConditional implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String authserver_self = System.getProperty("authserver_self");
        if ("true".equals(authserver_self)) {// authserver_self doesn't conf resource server
            return false;
        }
        String oauthswitch = conditionContext.getEnvironment().getProperty("app.oauth-switch-on");
        return "true".equalsIgnoreCase(oauthswitch);
    }
}