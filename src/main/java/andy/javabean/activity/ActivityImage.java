package andy.javabean.activity;

import andy.javabean.MediaData;
import lombok.Data;

// 活動圖片
@Data
public class ActivityImage {
	public static final String TABLE_NAME = "ACTI_IMAG";
	public static final String IMAG_NO = "IMAG_NO";		//	圖片編號	INT
	public static final String ACTI_NO = "ACTI_NO";		//	活動編號	INT
	public static final String IMG_NAME = "IMG_NAME";		//	圖片名稱	VARCHAR
	public static final String IMG_DATA = "IMG_DATA";		//	圖片資料	LONGLOB
	public static final String IMG_URL = "IMG_URL";		//	圖片連結	VARCHAR
	public static final String IMG_UPDATE = "IMG_UPDATE";		//	上傳日期	DATETIME
	
	private int id;
	private int actId;
	private MediaData image;
}
