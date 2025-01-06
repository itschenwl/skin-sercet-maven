package andy.javabean.column;

import andy.javabean.MediaData;
import lombok.Data;

// 專欄
@Data
public class ArticleColumn {
	public static final String TABLE_NAME = "ARTI_COLU";
	public static final String ARTI_COLU_NO = "ARTI_COLU_NO";		//	專欄編號	INT
	public static final String ARTI_COLU_NAME = "ARTI_COLU_NAME";		//	專欄名稱	VARCHAR
	public static final String IMG_NAME = "IMG_NAME";		//	圖片名稱	VARCHAR
	public static final String IMG_DATA = "IMG_DATA";		//	圖片資料	LONGBLOB
	public static final String IMG_URL = "IMG_URL";		//	圖片連結	VARCHAR
	public static final String IMG_UPDATE = "IMG_UPDATE";		//	上傳日期	DATETIME
	
	private int id;
	private String name;
	private MediaData image; 
}
