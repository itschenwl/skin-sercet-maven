package andy.javabean.column;

import andy.javabean.Comment;
import lombok.Data;

// 專欄留言
@Data
public class ColumnComment {
	public static final String TABLE_NAME = "COLU_COMM";
	public static final String COLU_COMM_NO = "COLU_COMM_NO";		//	專欄文章留言編號	INT
	public static final String COLU_ARTI_NO = "COLU_ARTI_NO";		//	專欄文章編號	INT
	public static final String USER_NO = "USER_NO";		//	用戶編號	CHAR
	public static final String COLU_COMM_COM_DATE = "COLU_COMM_COM_DATE";		//	留言日期	DATETIME
	public static final String COLU_COMM_COM_CONT = "COLU_COMM_COM_CONT";		//	留言內容	LONGTEXT
	public static final String COLU_COMM_REP_DATE = "COLU_COMM_REP_DATE";		//	官方回覆日期	DATETIME
	public static final String COLU_COMM_REP_CONT = "COLU_COMM_REP_CONT";		//	官方回覆內容	LONGTEXT
	
	private int id;
	private int colArtiId;
	private String userId;
	private Comment comment;
}
