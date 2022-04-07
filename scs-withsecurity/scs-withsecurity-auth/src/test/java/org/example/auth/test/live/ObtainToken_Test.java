package org.example.auth.test.live;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@Slf4j
public class ObtainToken_Test {

    static String auth_server = "http://localhost:8091/authServer";
//    static String auth_server = "http://localhost:9003/authServer";

    private Response obtainAccessToken(String clientId, String username, String password, String... scopes) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", "pwd123");
        params.add("username", username);
        params.add("password", password);
        if (scopes != null) {
            for (String scope : scopes) {
                params.add("scope", scope);
            }
        }
        return RestAssured.given().auth().preemptive().basic(clientId, "pwd123").and().with().params(params).when().post(auth_server + "/oauth/token");

//        return RestAssured.given().params(params).when().post(auth_server + "/oauth/token");
    }

    private Response obtainAccessToken4Client(String clientId, String client_secret,String... scopes) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("grant_type", "client_credentials");
        params.add("client_id", clientId);
        params.add("client_secret", client_secret);
        if (scopes != null) {
            for (String scope : scopes) {
                params.add("scope", scope);
            }
        }
        return RestAssured.given().auth().preemptive().basic(clientId, "pwd123").and().with().params(params).when().post(auth_server + "/oauth/token");

//        return RestAssured.given().params(params).when().post(auth_server + "/oauth/token");
    }

    @Test
    public void testObCorsFiltertainToken() {
        Response authServerResponse = obtainAccessToken("client_server", "tom", "123456");
        log.info("status="+authServerResponse.statusCode());
        final String accessToken = authServerResponse.jsonPath().getString("access_token");
        log.info(authServerResponse.print());
        assertNotNull(accessToken);

    }

    @Test
    public void testObtainTokenClient() {
        Response authServerResponse = obtainAccessToken4Client("client_server", "pwd123");
        int status = authServerResponse.statusCode();
        log.info("status="+status);
        final String accessToken = authServerResponse.jsonPath().getString("access_token");
        assertNotNull(accessToken);
        log.info(authServerResponse.print());

    }

    private Response obtainRefreshToken(String clientId, final String refreshToken) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", clientId);
        params.add("refresh_token", refreshToken);
        return RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with().params(params).when().post(auth_server + "/oauth/token");
    }

    @Test
    public void testRefreshToken(){
        Response authServerResponse = obtainAccessToken("sampleclient", "user1", "123", "foo");
        final String accessToken = authServerResponse.jsonPath().getString("access_token");
        final String refresh_token = authServerResponse.jsonPath().getString("refresh_token");
        assertNotNull(accessToken);
        final String scope = authServerResponse.jsonPath().getString("scope");
        assertEquals(scope, "foo");
        log.info(authServerResponse.print());

        Response refreshTokenResponse = obtainRefreshToken("sampleclient",refresh_token);
        final String accessToken2 = refreshTokenResponse.jsonPath().getString("access_token");
        assertNotNull(accessToken2);
    }

}
