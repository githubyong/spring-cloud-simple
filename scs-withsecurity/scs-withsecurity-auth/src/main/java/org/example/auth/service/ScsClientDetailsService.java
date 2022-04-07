package org.example.auth.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Lazy
@Service
public class ScsClientDetailsService extends JdbcClientDetailsService {

	public ScsClientDetailsService(DataSource dataSource) {
		super(dataSource);
		setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
	}

}