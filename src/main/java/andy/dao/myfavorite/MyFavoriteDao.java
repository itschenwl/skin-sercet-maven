package andy.dao.myfavorite;

import java.util.List;

import andy.javabean.activity.Activity;
import andy.javabean.column.ColumnArticle;
import andy.javabean.community.CommunityArticle;
import andy.javabean.product.Product;

public interface MyFavoriteDao {
	
	int insertActivity(Integer id, String userId) throws Exception;
	int deleteActivity(Integer id, String userId) throws Exception;
	List<Activity> findActivities(String userId) throws Exception;

	int insertCommunity(Integer id, String userId) throws Exception;
	int deleteCommunity(Integer id, String userId) throws Exception;
	List<CommunityArticle> findCommunities(String userId) throws Exception;

	int insertColumn(Integer id, String userId) throws Exception;
	int deleteColumn(Integer id, String userId) throws Exception;
	List<ColumnArticle> findColumns(String userId) throws Exception;

	int insertProduct(String id, String userId) throws Exception;
	int deleteProduct(String id, String userId) throws Exception;
	List<Product> findProducts(String userId) throws Exception;
	
}
