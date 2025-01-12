package andy.dao.activity;

import java.util.List;

import andy.javabean.ArticleComment;
import andy.javabean.activity.Activity;
import andy.javabean.activity.ActivityJoin;

public interface ActivityDao {
	int insert(ArticleComment item) throws Exception;
	int update(ArticleComment item) throws Exception;
	int deleteComment(Integer id) throws Exception;
	List<ArticleComment> findAllComment(Integer actId) throws Exception;

	int insert(ActivityJoin item) throws Exception;
	int deleteJoin(Integer id, String userId) throws Exception;

	List<Activity> findAll(String userId) throws Exception;
}
