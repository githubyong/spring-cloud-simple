
package org.example.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ScsAuthenticationFailureEvenHandler implements ApplicationListener<AbstractAuthenticationFailureEvent> {

	/**
	 * Handle an application event.
	 *
	 * @param event the event to respond to
	 */
	@Override
	public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
		AuthenticationException authenticationException = event.getException();
		Authentication authentication = (Authentication) event.getSource();

		handle(authenticationException, authentication);
	}

	/**
	 * 处理登录成功方法
	 * <p>
	 *
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication          登录的authenticationException 对象
	 */
	public  void handle(AuthenticationException authenticationException, Authentication authentication){
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
	}
}
