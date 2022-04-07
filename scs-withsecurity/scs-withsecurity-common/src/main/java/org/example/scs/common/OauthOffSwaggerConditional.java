package org.example.scs.common;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OauthOffSwaggerConditional implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String oauthswitch = conditionContext.getEnvironment().getProperty("app.oauth-switch-on");
        String swaggerswitch = conditionContext.getEnvironment().getProperty("app.oauth-swagger-on");
        return !"true".equalsIgnoreCase(oauthswitch) && "true".equalsIgnoreCase(swaggerswitch) ;
    }
}