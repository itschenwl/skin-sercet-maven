package andy.javabean;

import lombok.Data;

// 文章留言
@Data
public class ArticleComment {
	private int id;
	private int artId;
	private String userId;
	private Comment comment;
}
