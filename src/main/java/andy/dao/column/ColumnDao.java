package andy.dao.column;

import java.util.List;

import andy.javabean.column.ColumnArticle;
import andy.javabean.column.ColumnComment;

public interface ColumnDao {
	int insert(ColumnComment item) throws Exception;
	int update(ColumnComment item) throws Exception;
	int deleteComment(Integer id) throws Exception;
	List<ColumnComment> findAllComment(Integer artId) throws Exception;

	List<ColumnArticle> findAll(String userId) throws Exception;
}
