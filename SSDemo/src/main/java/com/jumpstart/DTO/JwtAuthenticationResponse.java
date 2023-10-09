package com.jumpstart.DTO;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String message;
    private String roleName; // New field for role name
    private String userName; // New field for first name
    private Long userId; // New field for user ID
    private long storeId;
    private String storeName;
    private String storeAddress;

	

	public JwtAuthenticationResponse(String accessToken, String message, String roleName,
			String userName, Long userId, long storeId, String storeName, String storeAddress) {
		this.accessToken = accessToken;
		this.message = message;
		this.roleName = roleName;
		this.userName = userName;
		this.userId = userId;
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeAddress = storeAddress;
	}



	public String getAccessToken() {
		return accessToken;
	}



	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}



	public String getTokenType() {
		return tokenType;
	}



	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public long getStoreId() {
		return storeId;
	}



	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}



	public String getStoreName() {
		return storeName;
	}



	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}



	public String getStoreAddress() {
		return storeAddress;
	}



	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}


	
	

}
