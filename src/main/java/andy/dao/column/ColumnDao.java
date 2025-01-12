package andy.dao.column;

import java.util.List;

import andy.javabean.ArticleComment;
import andy.javabean.column.ColumnArticle;

public interface ColumnDao {
	int insert(ArticleComment item) throws Exception;
	int update(ArticleComment item) throws Exception;
	int deleteComment(Integer id) throws Exception;
	List<ArticleComment> findAllComment(Integer artId) throws Exception;

	List<ColumnArticle> findAll(String userId) throws Exception;
}
