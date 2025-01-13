package andy.javabean.community;

import andy.javabean.Article;
import lombok.Data;

// 社群文章
@Data
public class CommunityArticle {
	public static final String TABLE_NAME = "COMM_ARTI";
	public static final String COMM_ARTI_NO = "COMM_ARTI_NO";		//	社群文章編號	INT
	public static final String COMM_NO = "COMM_NO";		//	社群編號	INT
	public static final String USER_NO = "USER_NO";		//	用戶編號	CHAR
	public static final String COMM_ARTI_NAME = "COMM_ARTI_NAME";		//	文章標題	VARCHAR
	public static final String COMM_ARTI_CONT = "COMM_ARTI_CONT";		//	文章內容	LONGTEXT
	public static final String COMM_PUB_DATE = "COMM_PUB_DATE";		//	發表日期	DATETIME
	
	private int id;
	//private int commId;
	private String userId;
	private String userNickName;
	private Article comment;
	private Boolean isFavorite;        // 是否喜歡
	private Integer favoriteCount;         // 喜歡人數
	private Boolean isComment;         // 是否有留言
	private Integer commentCount;          // 留言人數
	private Boolean isCollection;      // 是否收藏
	private Integer collectionCount;        // 收藏人數
}
