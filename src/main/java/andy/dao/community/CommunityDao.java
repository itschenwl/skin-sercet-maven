package andy.dao.community;

import java.util.List;

import andy.javabean.community.CommunityArticle;
import andy.javabean.community.CommunityComment;

public interface CommunityDao {
	int insert(CommunityComment item) throws Exception;
	int update(CommunityComment item) throws Exception;
	int deleteComment(Integer id) throws Exception;
	List<CommunityComment> findAllComment(Integer artId) throws Exception;

	List<CommunityArticle> findAll(String userId) throws Exception;
}
