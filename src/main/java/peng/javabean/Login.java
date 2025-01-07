package peng.javabean;

import lombok.Data;

@Data
public class Login {
	public static final String TABLE_NAME = "USER_NUMB";
	public static final String USER_NO = "USER_NO";
	public static final String USERNAME = "USERNAME";
	public static final String USER_PASSWORD = "USER_PASSWORD";
	
	private int userNo; // 用戶編號
    private String username; // 帳號
    private String password; // 密碼
    
    public Login() {
        
    }
    public Login(Integer userNo, String username, String password) {
        super();
        this.userNo = userNo;
        this.username = username;
        this.password = password;
    }
    
    
    public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
