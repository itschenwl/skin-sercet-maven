package andy.dao.myfavorite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import andy.javabean.Article;
import andy.javabean.activity.Activity;
import andy.javabean.column.ColumnArticle;
import andy.javabean.community.CommunityArticle;
import andy.javabean.product.Product;
import andy.javabean.user.User;
import util.ServiceLocator;

public class MyFavoriteDaoImpl implements MyFavoriteDao {
	DataSource dataSource;

	public MyFavoriteDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insertActivity(Integer id, String userId) throws SQLException {
		String sql = "insert into MFACT (" +
				Activity.ACTI_NO + ", " + User.USER_NO +
			") values(?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	@Override
	public int deleteActivity(Integer id, String userId) throws SQLException {
		String sql = "delete from MFACT where " + Activity.ACTI_NO + " = ? AND " + User.USER_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<Activity> findActivities(String userId) throws SQLException {
		String sql = "select (select COUNT(*) from ACTI_COMM AS AC where ACTI_NO = A.ACTI_NO) as commcount ,"
				+ " A.*"
				+ " from MFACT as M join ACTI as A"
				+ " where M.ACTI_NO = A.ACTI_NO AND M.USER_NO = ?;";
		List<Activity> items = new ArrayList<Activity>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Activity item = new Activity();
				item.setId(rs.getInt(Activity.ACTI_NO));
				item.setName(rs.getString(Activity.ACTI_NAME));
				item.setDescription(rs.getString(Activity.ACTI_DESC));
				item.setStartDate(rs.getTimestamp(Activity.START_DATE));
				item.setEndDate(rs.getTimestamp(Activity.END_DATE));
				item.setDuring(rs.getInt(Activity.ACTI_TIME));
				item.setPrice(rs.getInt(Activity.PRICE));
				item.setImageId(rs.getInt(Activity.IMAG_NO));
				item.setState(rs.getInt(Activity.STATE));
				item.setRegStartDate(rs.getTimestamp(Activity.REGS_DATE));
				item.setRegEndDate(rs.getTimestamp(Activity.REGE_DATE));
				item.setMaxCount(rs.getInt(Activity.MAXCOUNT));
				item.setRegCount(rs.getInt(Activity.REGCOUNT));
				item.setIsFavorite(false);
				item.setFavoriteCount((int)(Math.random()*100));
				item.setIsComment(false);
				item.setCommentCount(rs.getInt("commcount"));
				item.setIsCollection(true);
				item.setCollectionCount((int)(Math.random()*100));
				items.add(item);
			}
		} 
		return items;
	}

	// insert into MFCOMA (COMM_ARTI_NO , USER_NO) values(1, 'U00000001');
	@Override
	public int insertCommunity(Integer id, String userId) throws Exception {
		String sql = "insert into MFCOMA (" +
				CommunityArticle.COMM_ARTI_NO + ", " + User.USER_NO +
			") values(?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	// delete from MFCOMA where COMM_ARTI_NO = 1 and USER_NO = 'U00000001';
	@Override
	public int deleteCommunity(Integer id, String userId) throws Exception {
		String sql = "delete from MFCOMA where " +
				CommunityArticle.COMM_ARTI_NO + " = ? AND " + User.USER_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<CommunityArticle> findCommunities(String userId) throws Exception {
		String sql = "select (select COUNT(*) from COMM_COMM AS CC where CC.COMM_ARTI_NO = C.COMM_ARTI_NO) as commcount ,"
				+ " C.*"
				+ " from MFCOMA as M join COMM_ARTI as C"
				+ " where M.COMM_ARTI_NO = C.COMM_ARTI_NO AND M.USER_NO = ?;";
		List<CommunityArticle> items = new ArrayList<CommunityArticle>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CommunityArticle community = new CommunityArticle();
				community.setId(rs.getInt(CommunityArticle.COMM_ARTI_NO));
				//community.setCommId(rs.getInt(CommunityArticle.COMM_NO));
				community.setUserId(rs.getString(CommunityArticle.USER_NO));
				Article article = new Article();
				article.setTitle(rs.getString(CommunityArticle.COMM_ARTI_NAME));
				article.setContent(rs.getString(CommunityArticle.COMM_ARTI_CONT));
				article.setPostDate(rs.getTimestamp(CommunityArticle.COMM_PUB_DATE));
				community.setComment(article);
				community.setIsFavorite(false);
				community.setFavoriteCount((int)(Math.random()*100));
				community.setIsComment(false);
				community.setCommentCount(rs.getInt("commcount"));
				community.setIsCollection(true);
				community.setCollectionCount((int)(Math.random()*100));
				items.add(community);
			}
		} 
		return items;
	}

	@Override
	public int insertColumn(Integer id, String userId) throws Exception {
		String sql = "insert into MFCOLA (" +
				ColumnArticle.COLU_ARTI_NO + ", " + User.USER_NO +
			") values(?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	@Override
	public int deleteColumn(Integer id, String userId) throws Exception {
		String sql = "delete from MFCOLA where " +
				ColumnArticle.COLU_ARTI_NO + " = ? AND " + User.USER_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<ColumnArticle> findColumns(String userId) throws Exception {
		String sql = "select (select COUNT(*) from COLU_COMM AS CC where CC.COLU_ARTI_NO = C.COLU_ARTI_NO) as commcount," +
				" C.* from MFCOLA as M join COLU_ARTI as C where M.COLU_ARTI_NO = C.COLU_ARTI_NO AND M.USER_NO = ?;";
	List<ColumnArticle> items = new ArrayList<ColumnArticle>();
	try (
			Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
		ps.setString(1, userId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ColumnArticle column = new ColumnArticle();
			column.setId(rs.getInt(ColumnArticle.COLU_ARTI_NO));
			column.setArtiColID(rs.getInt(ColumnArticle.ARTI_COLU_NO));
			column.setUserId(rs.getString(ColumnArticle.USER_NO));
			Article article = new Article();
			article.setTitle(rs.getString(ColumnArticle.COLU_ARTI_NAME));
			article.setContent(rs.getString(ColumnArticle.COLU_ARTI_CONT));
			article.setPostDate(rs.getTimestamp(ColumnArticle.COLU_ARTI_DATE));
			column.setArticle(article);
			column.setIsFavorite(false);
			column.setFavoriteCount((int)(Math.random()*100));
			column.setIsComment(false);
			column.setCommentCount(rs.getInt("commcount"));
			column.setIsCollection(true);
			column.setCollectionCount((int)(Math.random()*100));
			items.add(column);
		}
	} 
	return items;
	}

	@Override
	public int insertProduct(String id, String userId) throws Exception {
		String sql = "insert into MFPRO (" +
				Product.PROD_NO + ", " + User.USER_NO +
			") values(?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	@Override
	public int deleteProduct(String id, String userId) throws Exception {
		String sql = "delete from MFPRO where " +
				Product.PROD_NO + " = ? AND " + User.USER_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<Product> findProducts(String userId) throws Exception {
		String sql = "select P.* from MFPRO as M join PROD as P where M.PROD_NO = P.PROD_NO AND M.USER_NO = ?;";
	List<Product> items = new ArrayList<Product>();
	try (
			Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
		ps.setString(1, userId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Product product = new Product();
			product.setId(rs.getString(Product.PROD_NO));
			product.setBrandId(rs.getInt(Product.BRAND_NO));
			product.setTypeId(rs.getInt(Product.TYPE_NO));
			product.setSearchCount(rs.getInt(Product.PROD_COUNT));
			product.setName(rs.getString(Product.PROD_NAME));
			product.setState(rs.getInt(Product.PROD_STATE));
			product.setPrice(rs.getInt(Product.PRICE));
			product.setIsFavorite(true);
			items.add(product);
		}
	} 
	return items;
	}

}
