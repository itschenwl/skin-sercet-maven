package andy.dao.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import andy.javabean.Article;
import andy.javabean.ArticleComment;
import andy.javabean.Comment;
import andy.javabean.Message;
import andy.javabean.community.CommunityArticle;
import util.ServiceLocator;

public class CommunityDaoImpl implements CommunityDao {
	public static final String TABLE_NAME = "COMM_COMM";
	public static final String COMM_COMM_NO = "COMM_COMM_NO";		//	社群留言編號	INT
	public static final String COMM_ARTI_NO = "COMM_ARTI_NO";		//	社群文章編號	INT
	public static final String USER_NO = "USER_NO";		//	用戶編號	CHAR
	public static final String COM_DATE = "COM_DATE";		//	留言日期	DATETIME
	public static final String COM_CON = "COM_CON";		//	留言內容	LONGTEXT
	public static final String REP_DATE = "REP_DATE";		//	官方回覆日期	DATETIME
	public static final String REP_CON = "REP_CON";		//	官方回覆內容	LONGTEXT
	DataSource dataSource;

	public CommunityDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(ArticleComment item) throws SQLException {
		String sql = "insert into " + TABLE_NAME + " (" +
				COMM_ARTI_NO + ", " + USER_NO + ", " + 
				COM_DATE + ", " + COM_CON + ", " +
				REP_DATE + ", " + REP_CON +
			") values(?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getArtId());
			ps.setString(2, item.getUserId());
			Comment comment = item.getComment();
			Message message = comment.getComment();
			Message reply = comment.getReply();
			ps.setTimestamp(3, message.getUpdateDate());
			ps.setString(4, message.getMessage());
			ps.setTimestamp(5, reply.getUpdateDate());
			ps.setString(6, reply.getMessage());
			return ps.executeUpdate();
		}
	}

	@Override
	public int update(ArticleComment item) throws SQLException {
		String sql = "update " + TABLE_NAME + " set " + 
				COMM_ARTI_NO + " = ?, " + USER_NO + " = ?, " + 
				COM_DATE + " = ?, " + COM_CON + " = ?, " +
				REP_DATE + " = ?, " + REP_CON + " = ?" +
				" where " + COMM_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getArtId());
			ps.setString(2, item.getUserId());
			Comment comment = item.getComment();
			Message message = comment.getComment();
			Message reply = comment.getReply();
			ps.setTimestamp(3, message.getUpdateDate());
			ps.setString(4, message.getMessage());
			ps.setTimestamp(5, reply.getUpdateDate());
			ps.setString(6, reply.getMessage());
			ps.setInt(7, item.getId());
			return ps.executeUpdate();
		}
	}

	@Override
	public int deleteComment(Integer id) throws SQLException {
		String sql = "delete from " + TABLE_NAME + " where " + COMM_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<ArticleComment> findAllComment(Integer artId) throws SQLException {
		String sql = "select * from " + TABLE_NAME + " where " + COMM_ARTI_NO + " = ?;";
		List<ArticleComment> items = new ArrayList<ArticleComment>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setInt(1, artId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArticleComment item = new ArticleComment();
				item.setId(rs.getInt(COMM_COMM_NO));
				item.setArtId(rs.getInt(COMM_ARTI_NO));
				item.setUserId(rs.getString(USER_NO));
				Comment comment = new Comment();
				Message message = new Message();
				message.setMessage(rs.getString(COM_CON));
				message.setUpdateDate(rs.getTimestamp(COM_DATE));
				comment.setComment(message);
				Message reply = new Message();
				reply.setMessage(rs.getString(REP_CON));
				reply.setUpdateDate(rs.getTimestamp(REP_DATE));
				comment.setReply(reply);
				item.setComment(comment);
				items.add(item);
			}
		} 
		return items;
	}

	@Override
	public int insert(CommunityArticle item) throws SQLException {
		String sql = "insert into " + CommunityArticle.TABLE_NAME + " (" +
				CommunityArticle.USER_NO + ", " + 
				CommunityArticle.COMM_ARTI_NAME + ", " + CommunityArticle.COMM_ARTI_CONT + ", " +
				CommunityArticle.COMM_PUB_DATE +
			") values(?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, item.getUserId());
			Article article = item.getComment();
			ps.setString(2, article.getTitle());
			ps.setString(3, article.getContent());
			ps.setTimestamp(4, article.getPostDate());
			return ps.executeUpdate();
		}
	}

	@Override
	public int update(CommunityArticle item) throws SQLException {
		String sql = "update " + CommunityArticle.TABLE_NAME + " set " + 
				CommunityArticle.USER_NO + " = ?, " + 
				CommunityArticle.COMM_ARTI_NAME + " = ?, " + CommunityArticle.COMM_ARTI_CONT + " = ?, " +
				CommunityArticle.COMM_PUB_DATE + " = ?" +
				" where " + CommunityArticle.COMM_ARTI_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, item.getUserId());
			Article article = item.getComment();
			ps.setString(2, article.getTitle());
			ps.setString(3, article.getContent());
			ps.setTimestamp(4, article.getPostDate());
			ps.setInt(5, item.getId());
			return ps.executeUpdate();
		}
	}

	@Override
	public int deleteArticle(Integer id) throws SQLException {
		String sql = "delete from " + CommunityArticle.TABLE_NAME + " where " + CommunityArticle.COMM_ARTI_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<CommunityArticle> findAll(String userId) throws SQLException {
		String sql = "select CA.*, (CASE WHEN M.USER_NO IS NOT NULL THEN true ELSE false END) as isCollection ," +
				" (select COUNT(*) from COMM_COMM AS CC where CC.COMM_ARTI_NO = CA.COMM_ARTI_NO) as commcount" +
				" from " + CommunityArticle.TABLE_NAME + " as CA left join " +
				" (select * from MFCOMA where USER_NO = ?) as M on CA.COMM_ARTI_NO = M.COMM_ARTI_NO;";
		List<CommunityArticle> items = new ArrayList<CommunityArticle>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CommunityArticle item = new CommunityArticle();
				item.setId(rs.getInt(CommunityArticle.COMM_ARTI_NO));
				//item.setCommId(rs.getInt(CommunityArticle.COMM_NO));
				item.setUserId(rs.getString(CommunityArticle.USER_NO));
				Article article = new Article();
				article.setTitle(rs.getString(CommunityArticle.COMM_ARTI_NAME));
				article.setContent(rs.getString(CommunityArticle.COMM_ARTI_CONT));
				article.setPostDate(rs.getTimestamp(CommunityArticle.COMM_PUB_DATE));
				item.setComment(article);
				item.setIsFavorite(false);
				item.setFavoriteCount((int)(Math.random()*100));
				item.setIsComment(false);
				item.setCommentCount(rs.getInt("commcount"));
				item.setIsCollection(rs.getBoolean("isCollection"));
				item.setCollectionCount((int)(Math.random()*100));
				items.add(item);
			}
		} 
		return items;
	}
}
