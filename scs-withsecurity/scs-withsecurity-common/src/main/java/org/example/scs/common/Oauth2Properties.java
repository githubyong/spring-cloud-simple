
package org.example.scs.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * oauth2 参数配置
 */
@Data
@Configuration
@RefreshScope
public class Oauth2Properties {
	/**
	 * 放行终端配置，网关不校验此处的终端
	 */
	@Value("#{'${app.ignore.clients:na}'.split(',')}")
	private List<String> clients = new ArrayList<>();
	/**
	 * 放行url,放行的url不再被安全框架拦截
	 */
	@Value("#{'${app.ignore.urls:test}'.split(',')}")
	private List<String> ignoreUrls = new ArrayList<>();

	@Value("${security.oauth2.client.access-token-uri:na}")
	String access_token_uri;

	@Value("${security.oauth2.loadBalancedAuthServer:na}")
	String loadBalancedAuthServer;

	@Value("${security.oauth2.client.client-id:na}")
	String client_id;

	@Value("${security.oauth2.client.client-secret:na}")
	String client_secret;

	@Value("${security.oauth2.signingKey:scsauth}")
	private String signingKey;
}
