package org.example.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
public class ScsAuthenticationSuccessEventHandler implements ApplicationListener<AuthenticationSuccessEvent> {
	/**
	 * Handle an application event.
	 *
	 * @param event the event to respond to
	 */
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication authentication = (Authentication) event.getSource();
		if (!CollectionUtils.isEmpty(authentication.getAuthorities())) {
			handle(authentication);
		}
	}

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 *
	 * @param authentication 登录对象
	 */
	public void handle(Authentication authentication){
		log.info("用户：{} 登录成功", authentication.getPrincipal());
	}
}