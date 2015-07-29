package cn.thiki.dmo;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 6088584373171977182L;

	/** id **/
	private Integer id;

	/** 用户名 **/
	private String userName;

	/** 登录密码 **/
	private String password;

	/** 注册邮箱 **/
	private String email;

	/** clientId **/
	private String clientId;

	/** resourceIds **/
	private String resourceIds;

	/** client_secret **/
	private String client_secret;

	/** scopes **/
	private String scopes;

	/** grantedTypes **/
	private String grantedTypes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String clientSecret) {
		client_secret = clientSecret;
	}

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public String getGrantedTypes() {
		return grantedTypes;
	}

	public void setGrantedTypes(String grantedTypes) {
		this.grantedTypes = grantedTypes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [clientId=").append(clientId).append(
				", client_secret=").append(client_secret).append(", email=")
				.append(email).append(", grantedTypes=").append(grantedTypes)
				.append(", id=").append(id).append(", password=").append(
						password).append(", resourceIds=").append(resourceIds)
				.append(", scopes=").append(scopes).append(", username=")
				.append(userName).append("]");
		return builder.toString();
	}

}
