package andy.dao.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import andy.javabean.Article;
import andy.javabean.Comment;
import andy.javabean.Message;
import andy.javabean.column.ColumnArticle;
import andy.javabean.column.ColumnComment;
import andy.javabean.community.CommunityArticle;
import andy.javabean.community.CommunityComment;
import util.ServiceLocator;

public class CommunityDaoImpl implements CommunityDao {

	DataSource dataSource;

	public CommunityDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(CommunityComment item) throws SQLException {
		String sql = "insert into " + CommunityComment.TABLE_NAME + " (" +
				CommunityComment.COMM_ARTI_NO + ", " + CommunityComment.USER_NO + ", " + 
				CommunityComment.COM_DATE + ", " + CommunityComment.COM_CON + ", " +
				CommunityComment.REP_DATE + ", " + CommunityComment.REP_CON +
			") values(?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getCommArtiId());
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
	public int update(CommunityComment item) throws SQLException {
		String sql = "update " + CommunityComment.TABLE_NAME + " set " + 
				CommunityComment.COMM_ARTI_NO + " = ?, " + CommunityComment.USER_NO + " = ?, " + 
				CommunityComment.COM_DATE + " = ?, " + CommunityComment.COM_CON + " = ?, " +
				CommunityComment.REP_DATE + " = ?, " + CommunityComment.REP_CON + " = ?" +
				" where " + CommunityComment.COMM_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getCommArtiId());
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
		String sql = "delete from " + CommunityComment.TABLE_NAME + " where " + CommunityComment.COMM_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<CommunityComment> findAllComment(Integer artId) throws SQLException {
		String sql = "select * from " + CommunityComment.TABLE_NAME + " where " + CommunityComment.COMM_COMM_NO + " = ?;";
		List<CommunityComment> items = new ArrayList<CommunityComment>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setInt(1, artId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CommunityComment item = new CommunityComment();
				item.setId(rs.getInt(CommunityComment.COMM_COMM_NO));
				item.setCommArtiId(rs.getInt(CommunityComment.COMM_ARTI_NO));
				item.setUserId(rs.getString(CommunityComment.USER_NO));
				Comment comment = new Comment();
				Message message = new Message();
				message.setMessage(rs.getString(CommunityComment.COM_CON));
				message.setUpdateDate(rs.getTimestamp(CommunityComment.COM_DATE));
				comment.setComment(message);
				Message reply = new Message();
				reply.setMessage(rs.getString(CommunityComment.REP_CON));
				reply.setUpdateDate(rs.getTimestamp(CommunityComment.REP_DATE));
				comment.setReply(reply);
				item.setComment(comment);
				items.add(item);
			}
		} 
		return items;
	}

	@Override
	public List<CommunityArticle> findAll(String userId) throws SQLException {
		String sql = "select CA.*, (CASE WHEN M.USER_NO = ? THEN true ELSE false END) as isCollection ," +
				" (select COUNT(*) from COMM_COMM AS CC where CC.COMM_ARTI_NO = CA.COMM_ARTI_NO) as commcount" +
				" from " + CommunityArticle.TABLE_NAME + " as CA left join MFCOMA as M on CA.COMM_ARTI_NO = M.COMM_ARTI_NO;";
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
