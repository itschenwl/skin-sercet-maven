package andy.dao.community;

import java.util.List;

import andy.javabean.ArticleComment;
import andy.javabean.community.CommunityArticle;

public interface CommunityDao {
	int insert(ArticleComment item) throws Exception;
	int update(ArticleComment item) throws Exception;
	int deleteComment(Integer id) throws Exception;
	List<ArticleComment> findAllComment(Integer artId) throws Exception;
	
	int insert(CommunityArticle item) throws Exception;
	int update(CommunityArticle item) throws Exception;
	int deleteArticle(Integer id) throws Exception;
	List<CommunityArticle> findAll(String userId) throws Exception;
}
