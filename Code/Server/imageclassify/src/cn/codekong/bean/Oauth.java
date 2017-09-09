package cn.codekong.bean;

import java.util.Date;
 

public class Oauth {
	  private int oauth_id;
	  private String oauth_token;
	  private Date oauth_token_expiration;
      private User user;  
	  
		public int getOauth_id() {
			return oauth_id;
		}
		public void setOauth_id(int oauth_id) {
			this.oauth_id = oauth_id;
		}
		public String getOauth_token() {
			return oauth_token;
		}
		public void setOauth_token(String oauth_token) {
			this.oauth_token = oauth_token;
		}
		public Date getOauth_token_expiration() {
			return oauth_token_expiration;
		}
		public void setOauth_token_expiration(Date oauth_token_expiration) {
			this.oauth_token_expiration = oauth_token_expiration;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}  
}
