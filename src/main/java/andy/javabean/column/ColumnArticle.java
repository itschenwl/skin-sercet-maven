package andy.javabean.column;

import andy.javabean.Article;
import lombok.Data;

// 專欄文章
@Data
public class ColumnArticle {
	public static final String TABLE_NAME = "COLU_ARTI";
	public static final String COLU_ARTI_NO = "COLU_ARTI_NO";		//	專欄文章編號	INT
	public static final String ARTI_COLU_NO = "ARTI_COLU_NO";		//	專欄編號	INT
	public static final String USER_NO = "USER_NO";					//	用戶編號	CHAR
	public static final String COLU_ARTI_NAME = "COLU_ARTI_NAME";		//	文章標題	VARCHAR
	public static final String COLU_ARTI_CONT = "COLU_ARTI_CONT";		//	文章內容	LONGTEXT
	public static final String COLU_ARTI_DATE = "COLU_ARTI_DATE";		//	發表日期	DATETIME
	
	private int id;
	private int artiColID;
	private String userId;
	private Article article;
	private Boolean isFavorite;        // 是否喜歡
	private Integer favoriteCount;         // 喜歡人數
	private Boolean isComment;         // 是否有留言
	private Integer commentCount;          // 留言人數
	private Boolean isCollection;      // 是否收藏
	private Integer collectionCount;        // 收藏人數
}
