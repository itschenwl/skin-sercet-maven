package andy.javabean;

import lombok.Data;

// 留言回覆
@Data
public class Comment {
	private Message comment;
	private Message reply;
}
