package andy.javabean.activity;

import andy.javabean.Comment;
import lombok.Data;

// 活動留言
@Data
public class ActivityComment {
	public static final String TABLE_NAME = "ACTI_COMM";
	public static final String ACTI_COMM_NO = "ACTI_COMM_NO";		//	活動留言編號	INT
	public static final String ACTI_NO = "ACTI_NO";		//	活動編號	INT
	public static final String USER_NO = "USER_NO";		//	用戶編號	CHAR
	public static final String COM_DATE = "COM_DAT";		//	留言日期	DATETIME
	public static final String COM_CON = "COM_CON";		//	留言內容	LONGTEXT
	public static final String REP_DATE = "REP_DATE";		//	官方回覆日期	DATETIME
	public static final String REP_CON = "REP_CON";		//	官方回覆內容	LONGTEXT

	private int id;
	private int actId;
	private String userId;
	private Comment comment;
}
