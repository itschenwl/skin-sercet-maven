package andy.javabean.activity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

// 活動人員
@Data
public class ActivityJoin {
	public static final String TABLE_NAME = "ACTI_JOIN";		
	// ACTI_NO + USER_NO
	public static final String ACTI_NO = "ACTI_NO";		//	活動編號	INT
	public static final String USER_NO = "USER_NO";		//	會員編號	CHAR
	public static final String STATE = "STATE";		//	報名狀態	TINYINT
	public static final String REG_DATE = "REG_DATE";		//	報名日期	DATETIME
	
	private int actId;
	private String userId;
	private int state;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp registerDate;
}
