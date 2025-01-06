package andy.javabean;

import lombok.Data;

// 處理進度
@Data
public class Processing {
	public static final String TABLE_NAME = "PROC";
	public static final String PROC_NO = "PROC_NO";		//	處理類型編號	INT
	public static final String PROC_NAME = "PROC_NAME";		//	處理類型名稱	VARCHAR
	public static final String PROC_CONTENT = "PROC_CONTENT";		//	處理類型內容	LONGTEXT
	
	private int id;
	private String name;
	private String content;
}
