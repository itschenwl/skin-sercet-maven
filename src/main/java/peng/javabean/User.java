package peng.javabean;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class User {
	public static final String TABLE_NAME = "USER_NUMB";
	public static final String USER_NO = "USER_NO";
	public static final String USER_PASSWORD = "USER_PASSWORD";
	public static final String FULL_NAME = "FULL_NAME";
	public static final String NICK_NAME = "NICK_NAME";
	public static final String TELL_NUMBER = "TELL_NUMBER";
	public static final String BIRTHDAY = "BIRTHDAY";
	public static final String SEX = "SEX";
	public static final String MEM_LEVEL = "MEM_LEVEL";
	public static final String EMAIL = "EMAIL";
	public static final String IG = "IG";
	public static final String STATE = "STATE";
	public static final String PASS = "PASS";
	//public static final String PERSONAL_INFO = "PERSONAL_INFO";
	//public static final String AVATAR = "AVATAR";
public User() { }

public User(String userNo, String password) {
	super();
	this.userNo = userNo;
	this.password = password;
}
public User(
        String userNo, String name, String password,
        String nickname, String telNumber, Timestamp birthday,
        Integer sex, Integer memLevel, String email,
        String ig, Integer state, Boolean pass) {
    super();
    this.userNo = userNo;
    this.name = name;
    this.password = password;
    this.nickname = nickname;
    this.telNumber = telNumber;
    this.birthday = birthday;
    this.sex = sex;
    this.memLevel = memLevel;
    this.email = email;
    this.ig = ig;
    this.state = state;
    this.pass = pass;
}

	
	private String userNo; // 用戶編號
	private String name; //姓名
    private String password; // 密碼
    private String nickname; // 暱稱
    private String telNumber; //電話號碼
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp birthday; // 生日
    private Integer sex; // 性別，1男2女，0都不是(秘密)
    private Integer memLevel; //會員等級
    private String email; //信箱
    private String ig; //IG帳號
    private Integer state; //帳號狀態(是否停權)
    private Boolean pass; // 帳號登入狀態
    
    //get/set方法
	public String getUserNo() {
		return userNo;
	}
	
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTelephoneNumber() {
		return telNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telNumber = telephoneNumber;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getMemLevel() {
		return memLevel;
	}

	public void setMemLevel(Integer memLevel) {
		this.memLevel = memLevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIg() {
		return ig;
	}

	public void setIg(String ig) {
		this.ig = ig;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}
}

