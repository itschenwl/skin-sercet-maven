package andy.javabean;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Article {
	private String title;
	private String content;
	private Timestamp postDate;

}
