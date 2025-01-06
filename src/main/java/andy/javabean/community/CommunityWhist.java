package andy.javabean.community;

import andy.javabean.Comment;
import lombok.Data;

// 社群檢舉
@Data
public class CommunityWhist {
	public static final String TABLE_NAME = "COWH";
	public static final String COWH_NO = "COWH_NO";		//	檢舉編號	INT
	public static final String COMM_NO = "COMM_NO";		//	社群編號	INT
	public static final String PROC_NO = "PROC_NO";		//	處理進度	INT
	public static final String USER_NO = "USER_NO";		//	用戶編號	CHAR
	public static final String COWH_DATE = "COWH_DATE";		//	檢舉日期	DATE
	public static final String COWH_CONTENT = "COWH_CONTENT";		//	檢舉內容	LONGTEXT
	
	private int id;
	private int commId;
	private int procId;
	private String userId;
	private Comment content;
	//private Timestamp datetime;
	//private String content;
}
