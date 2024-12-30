package andy.javabean.maintain;

import java.sql.Timestamp;

public class Maintain {
	private Integer id;
	private String userId;
	private Timestamp reminder; 	// 通知時間
	private Long interval; 			// 間隔(小時)
	private String title; 			// 標題

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getReminder() {
		return reminder;
	}

	public void setReminder(Timestamp reminder) {
		this.reminder = reminder;
	}

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
