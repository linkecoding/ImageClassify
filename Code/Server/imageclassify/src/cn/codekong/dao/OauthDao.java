package cn.codekong.dao;

import cn.codekong.bean.Oauth;
import cn.codekong.bean.User;

public interface OauthDao {
     boolean saveOauth(Oauth oauth); 
     boolean deleteOauth(Oauth oauth);
     boolean updateOauth(Oauth oauth); 
     Oauth findOauthByOauthToken(String oauth_token);
     Oauth findOauthByOauthId(int oauth_id);
     Oauth findOauthByUser(User user);
}
