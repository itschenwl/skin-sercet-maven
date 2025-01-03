package andy.javabean.user;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

// 意見回饋
@Data
public class Feedback {
	public static final String TABLE_NAME = "USOP";
	public static final String UO_NO = "UO_NO";		//	意見編號	INT
	public static final String USER_NO = "USER_NO";	//	用戶編號	CHAR
	public static final String PROC_NO = "PROC_NO";	//	處理進度	INT
	public static final String CONTENT = "CONTENT";	//	用戶意見內容	LONGTEXT
	public static final String REPLY = "REPLY";		//	回覆內容	LONGTEXT
	public static final String UO_DATE = "UO_DATE";	//	用戶意見日期	DATETIME
	public static final String UO_REPDATE = "UO_REPDATE";	//	官方回覆日期	DATETIME
	
	private Integer id;
	private String userId;
	private Integer procId;
	private String content;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createDate;
	private String reply;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp replyDate;
}
