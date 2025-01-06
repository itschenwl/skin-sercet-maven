package andy.javabean.activity;

import andy.javabean.MediaData;
import lombok.Data;

// 活動影片
@Data
public class ActivityVideo {
	public static final String TABLE_NAME = "ACT_VIDEO";
	public static final String VIDEO_NO = "VIDEO_NO";		//	影片編號	INT
	public static final String ACTI_NO = "ACTI_NO";		//	活動編號	INT
	public static final String VIDEO_NAME = "VIDEO_NAME";		//	影片名稱	VARCHAR
	public static final String VIDEO_DATA = "VIDEO_DATA";		//	影片資料	LONGBLOB
	public static final String VIDEO_URL = "VIDEO_URL";		//	影片連結	VARCHAR
	public static final String VIDEO_UPDATE = "VIDEO_UPDATE";		//	上傳日期	DATETIME
	
	private int id;
	private int actId;
	private MediaData video;
}
