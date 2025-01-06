package andy.javabean;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MediaData {
	private String name;
	private Byte[] data;
	private String url;
	private Timestamp update;
}
