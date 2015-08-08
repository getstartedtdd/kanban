package cn.thiki.core.endpoint.store;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import cn.thiki.core.endpoint.dao.MybatisTokenDao;
import cn.thiki.core.endpoint.entity.MybatisOauth2AccessToken;
import cn.thiki.core.endpoint.entity.MybatisOauth2RefreshToken;


public class MybatisTokenStore implements TokenStore{
    
    private static final Logger LOG = LoggerFactory.getLogger(MybatisTokenStore.class);
    
    private MybatisTokenDao mybatisTokenDao;  
    
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        OAuth2Authentication authentication = null;  
        
        try {  
            MybatisOauth2AccessToken at=mybatisTokenDao.readAccessToken( token.getValue());  
            authentication = SerializationUtils.deserialize(at.getAuthentication());  
                      
        }  
        catch (EmptyResultDataAccessException e) {  
            if (LOG.isInfoEnabled()) {  
                LOG.info("Failed to find access token for token " + token);  
            }  
        }  
  
        return authentication;  
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;  
        if (token.getRefreshToken() != null) {  
            refreshToken = token.getRefreshToken().getValue();  
        }  
        MybatisOauth2AccessToken at=new MybatisOauth2AccessToken();  
        at.setTokenId(token.getValue());  
        at.setToken(SerializationUtils.serialize(token));  
        at.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));  
        at.setAuthentication(SerializationUtils.serialize(authentication));  
        at.setRefreshToken(refreshToken);  
        mybatisTokenDao.storeAccessToken(at); 
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;  
        
        try {  
            accessToken = SerializationUtils.deserialize(mybatisTokenDao.readAccessToken(tokenValue).getToken());             
        }  
        catch (EmptyResultDataAccessException e) {  
            if (LOG.isInfoEnabled()) {  
                LOG.info("Failed to find access token for token " + tokenValue);  
            }  
        }  
  
        return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        MybatisOauth2RefreshToken rt=new MybatisOauth2RefreshToken();  
        rt.setTokenId(refreshToken.getValue());  
        rt.setToken(SerializationUtils.serialize(refreshToken));  
        rt.setAuthentication(SerializationUtils.serialize(authentication));  
        mybatisTokenDao.storeRefreshToken(rt);  
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        ExpiringOAuth2RefreshToken refreshToken = null;  
        
        try {  
            refreshToken = SerializationUtils.deserialize(mybatisTokenDao.readRefreshToken(tokenValue).getToken());  
        }  
        catch (EmptyResultDataAccessException e) {  
            if (LOG.isInfoEnabled()) {  
                LOG.info("Failed to find refresh token for token " + tokenValue);  
            }  
        }  
  
        return refreshToken; 
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;  
        
        try {  
            String auth=authenticationKeyGenerator.extractKey(authentication);  
             MybatisOauth2AccessToken at=mybatisTokenDao.getAccessToken(auth);  
            if(null==at){  
                return null;  
            }else{  
                accessToken = SerializationUtils.deserialize(at.getToken());  
            }  
                  
        }  
        catch (EmptyResultDataAccessException e) {  
            if (LOG.isInfoEnabled()) {  
                LOG.debug("Failed to find access token for authentication " + authentication);  
            }  
        }  
  
        return accessToken; 
    }

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(
			String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
