package org.example.auth.test.mock;

import lombok.extern.slf4j.Slf4j;
import org.example.auth.AuthServerApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * 使用MockMvc进行测试，无需启动application
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
public class Mock_ObtainToken_Test {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String CLIENT_SECRET = "dragon";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    private ResultActions obtainAccessToken(String username, String password, String client_id, String... scopes) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", client_id);
        params.add("username", username);
        params.add("password", password);
        if (scopes != null) {
            for (String scope : scopes) {
                params.add("scope", scope);
            }
        }

        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(client_id, CLIENT_SECRET))
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
      return result;
    }


    @Test
    public void testObtainToken() throws Exception {
        ResultActions result = obtainAccessToken("user1", "pwd1", "client_1", "foo");
        result.andExpect(jsonPath("scope").value("foo"));
        log.info(result.andReturn().getResponse().getContentAsString());

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString()).get("access_token").toString();
        log.info("token="+token);
    }

}
