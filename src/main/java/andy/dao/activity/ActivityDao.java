package andy.dao.activity;

import java.util.List;

import andy.javabean.activity.Activity;
import andy.javabean.activity.ActivityComment;
import andy.javabean.activity.ActivityJoin;

public interface ActivityDao {
	int insert(ActivityComment item) throws Exception;
	int update(ActivityComment item) throws Exception;
	int deleteComment(Integer id) throws Exception;
	List<ActivityComment> findAllComment(Integer actId) throws Exception;

	int insert(ActivityJoin item) throws Exception;
	int deleteJoin(Integer id, String userId) throws Exception;
	List<Activity> findAllJoin(String userId) throws Exception;

	List<Activity> findAll(String userId) throws Exception;
}
