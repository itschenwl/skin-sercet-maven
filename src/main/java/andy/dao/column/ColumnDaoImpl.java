package andy.dao.column;

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
import andy.javabean.community.CommunityComment;
import util.ServiceLocator;

public class ColumnDaoImpl implements ColumnDao {

	DataSource dataSource;

	public ColumnDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(ColumnComment item) throws SQLException {
		String sql = "insert into " + ColumnComment.TABLE_NAME + " (" +
				ColumnComment.COLU_ARTI_NO + ", " + ColumnComment.USER_NO + ", " + 
				ColumnComment.COLU_COMM_COM_DATE + ", " + ColumnComment.COLU_COMM_COM_CONT + ", " +
				ColumnComment.COLU_COMM_REP_DATE + ", " + ColumnComment.COLU_COMM_REP_CONT +
			") values(?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getColArtiId());
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
	public int update(ColumnComment item) throws SQLException {
		String sql = "update " + ColumnComment.TABLE_NAME + " set " + 
				ColumnComment.COLU_ARTI_NO + " = ?, " + ColumnComment.USER_NO + " = ?, " + 
				ColumnComment.COLU_COMM_COM_DATE + " = ?, " + ColumnComment.COLU_COMM_COM_CONT + " = ?, " +
				ColumnComment.COLU_COMM_REP_DATE + " = ?, " + ColumnComment.COLU_COMM_REP_CONT + " = ?" +
				" where " + ColumnComment.COLU_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getColArtiId());
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
		String sql = "delete from " + ColumnComment.TABLE_NAME + " where " + ColumnComment.COLU_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<ColumnComment> findAllComment(Integer artId) throws Exception {
		String sql = "select * from " + ColumnComment.TABLE_NAME + " where " + ColumnComment.COLU_ARTI_NO + " = ?;";
		List<ColumnComment> items = new ArrayList<ColumnComment>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setInt(1, artId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ColumnComment item = new ColumnComment();
				item.setId(rs.getInt(ColumnComment.COLU_COMM_NO));
				item.setColArtiId(rs.getInt(ColumnComment.COLU_ARTI_NO));
				item.setUserId(rs.getString(ColumnComment.USER_NO));
				Comment comment = new Comment();
				Message message = new Message();
				message.setMessage(rs.getString(ColumnComment.COLU_COMM_COM_CONT));
				message.setUpdateDate(rs.getTimestamp(ColumnComment.COLU_COMM_COM_DATE));
				comment.setComment(message);
				Message reply = new Message();
				reply.setMessage(rs.getString(ColumnComment.COLU_COMM_REP_CONT));
				reply.setUpdateDate(rs.getTimestamp(ColumnComment.COLU_COMM_REP_DATE));
				comment.setReply(reply);
				item.setComment(comment);
				items.add(item);
			}
		} 
		return items;
	}
	
	@Override
	public List<ColumnArticle> findAll(String userId) throws SQLException {
		String sql = "select CA.*, (CASE WHEN M.USER_NO = ? THEN true ELSE false END) as isCollection" +
				" (select COUNT(*) from COLU_COMM AS CC where CC.COLU_ARTI_NO = CA.COLU_ARTI_NO) as commcount" +
				" from " + ColumnArticle.TABLE_NAME + " as CA left join MFCOLA as M on CA.COLU_ARTI_NO = M.COLU_ARTI_NO;";
		List<ColumnArticle> items = new ArrayList<ColumnArticle>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ColumnArticle item = new ColumnArticle();
				item.setId(rs.getInt(ColumnArticle.COLU_ARTI_NO));
				item.setArtiColID(rs.getInt(ColumnArticle.ARTI_COLU_NO));
				item.setUserId(rs.getString(ColumnArticle.USER_NO));
				Article article = new Article();
				article.setTitle(rs.getString(ColumnArticle.COLU_ARTI_NAME));
				article.setContent(rs.getString(ColumnArticle.COLU_ARTI_CONT));
				article.setPostDate(rs.getTimestamp(ColumnArticle.COLU_ARTI_DATE));
				item.setArticle(article);
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
