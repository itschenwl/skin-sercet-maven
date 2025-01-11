package andy.javabean.activity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

// 活動
@Data
public class Activity {
	public static final String TABLE_NAME = "ACTI";
	public static final String ACTI_NO = "ACTI_NO";		//	活動編號	INT
	public static final String ACTI_NAME = "ACTI_NAME";		//	活動標題	VARCHAR
	public static final String ACTI_DESC = "ACTI_DESC";		//	活動描述	LONGTEXT
	public static final String START_DATE = "START_DATE";		//	活動日期(起)	DATETIME
	public static final String END_DATE = "END_DATE";		//	活動日期(迄)	DATETIME
	public static final String ACTI_TIME = "ACTI_TIME";		//	活動時數	INT
	public static final String PRICE = "PRICE";		//	活動價格	INT
	public static final String IMAG_NO = "IMAG_NO";		//	圖片編號	INT
	public static final String STATE = "STATE";		//	活動狀態	TINYINT
	public static final String REGS_DATE = "REGS_DATE";		//	報名日期(起)	DATETIME
	public static final String REGE_DATE = "REGE_DATE";		//	報名日期(迄)	DATETIME
	public static final String MAXCOUNT = "MAXCOUNT";		//	報名總人數	INT
	public static final String REGCOUNT = "REGCOUNT";		//	目前報名人數	INT
	
	private int id;
	private String name;
	private String description;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp startDate;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp endDate;
	private int during;
	private int price;
	private int imageId;
	private int state;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp regStartDate;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp regEndDate;
	private int maxCount;
	private int regCount;
	private Boolean isFavorite;        // 是否喜歡
	private Integer favoriteCount;         // 喜歡人數
	private Boolean isComment;         // 是否有留言
	private Integer commentCount;          // 留言人數
	private Boolean isCollection;      // 是否收藏
	private Integer collectionCount;        // 收藏人數
}
