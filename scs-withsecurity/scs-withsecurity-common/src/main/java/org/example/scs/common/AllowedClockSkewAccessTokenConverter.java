package org.example.scs.common;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Date;
import java.util.Map;

public class AllowedClockSkewAccessTokenConverter extends DefaultAccessTokenConverter {


    private long allowedClockSkewSeconds = 0;

    public AllowedClockSkewAccessTokenConverter(long allowedClockSkewSeconds) {
        this.allowedClockSkewSeconds = allowedClockSkewSeconds;
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) super.extractAccessToken(value, map);
        if (map.containsKey(EXP)) {
            token.setExpiration(new Date((Long) map.get(EXP) * 1000L + allowedClockSkewSeconds * 1000L));
        }
        return token;
    }
}
