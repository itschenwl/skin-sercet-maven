package andy.javabean.community;

import andy.javabean.Comment;
import lombok.Data;

// 社群留言
@Data
public class CommunityComment {
	public static final String TABLE_NAME = "COMM_COMM";
	public static final String COMM_COMM_NO = "COMM_COMM_NO";		//	社群留言編號	INT
	public static final String COMM_ARTI_NO = "COMM_ARTI_NO";		//	社群文章編號	INT
	public static final String USER_NO = "USER_NO";		//	用戶編號	CHAR
	public static final String COM_DATE = "COM_DATE";		//	留言日期	DATETIME
	public static final String COM_CON = "COM_CON";		//	留言內容	LONGTEXT
	public static final String REP_DATE = "REP_DATE";		//	官方回覆日期	DATETIME
	public static final String REP_CON = "REP_CON";		//	官方回覆內容	LONGTEXT
	
	private int id;
	private int comArtId;
	private String userId;
	private Comment comment;
}
