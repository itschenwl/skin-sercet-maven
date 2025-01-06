package andy.javabean;

import lombok.Data;

@Data
public class Favorite<T> {
	private T id;
	private String userId;
}
