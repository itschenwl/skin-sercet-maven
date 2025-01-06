package andy.javabean;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Message {
	private Timestamp updateDate;
	private String message;
}
