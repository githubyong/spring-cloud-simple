package org.example.scs.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Conditional(OauthOnConditional.class)
public class OauthOnSwagger2Config {


    @Autowired
    Oauth2Properties oauth2Properties;


    @Bean
    public Docket productApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
//                .securityContexts(Arrays.asList(clientSecurityContext()))
                .securitySchemes(Arrays.asList(clientCredentialsGrant(), passwordGrant()))
//                .securitySchemes(Arrays.asList(clientCredentialsGrant()))
                ;


    }


    @Bean
    public SecurityConfiguration securityConfiguration() {
        return SecurityConfigurationBuilder.builder()
                .clientId(oauth2Properties.getClient_id())
//                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    final String PASSWORD_AUTHORIZATION = "oauth2_password";
    final String CLIENT_CREDENTIAL_AUTHORIZATION = "oauth2_client_credentials";


    private SecurityScheme passwordGrant() {
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(oauth2Properties.getAccess_token_uri());
        return new OAuthBuilder().name(PASSWORD_AUTHORIZATION).grantTypes(Arrays.asList(passwordCredentialsGrant)).scopes(Arrays.asList(scopes())).build();
//        return new OAuth("oauth2", Arrays.asList(scopes()), Arrays.asList(passwordCredentialsGrant));
    }

    private SecurityScheme clientCredentialsGrant() {
        GrantType clientCredentialsGrant = new ClientCredentialsGrant(oauth2Properties.getAccess_token_uri());
//        return new OAuth("clientCredentialsGrant", Arrays.asList(scopes()), Arrays.asList(clientCredentialsGrant));
        return new OAuthBuilder().name(CLIENT_CREDENTIAL_AUTHORIZATION).scopes(Arrays.asList(scopes())).grantTypes(Arrays.asList(clientCredentialsGrant)).build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(oauthSecurityReferences()).build();
    }


    protected List<SecurityReference> oauthSecurityReferences() {
        return Arrays.asList(new SecurityReference(PASSWORD_AUTHORIZATION, scopes()),
                new SecurityReference(CLIENT_CREDENTIAL_AUTHORIZATION, scopes()));
    }


    private AuthorizationScope[] scopes() {
       /* ClientDetails clientDetails = clientDetailsService.loadClientByClientId(oauth2Properties.getClient_id());
        List<AuthorizationScope> authorizationScopes = new ArrayList<>();
        for (String scope : clientDetails.getScope()) {
            authorizationScopes.add(new AuthorizationScope(scope, "module_" + scope));
        }*/
        return new AuthorizationScope[]{new AuthorizationScope("order", "module_order")};
    }

    @Value("${spring.application.name}")
    private String moduleName;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("module_" + moduleName)
                .build();
    }
}
