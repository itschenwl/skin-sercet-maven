package andy.dao.column;

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
import andy.javabean.column.ColumnArticle;
import util.ServiceLocator;

public class ColumnDaoImpl implements ColumnDao {

	public static final String TABLE_NAME = "COLU_COMM";
	public static final String COLU_COMM_NO = "COLU_COMM_NO";		//	專欄文章留言編號	INT
	public static final String COLU_ARTI_NO = "COLU_ARTI_NO";		//	專欄文章編號	INT
	public static final String USER_NO = "USER_NO";		//	用戶編號	CHAR
	public static final String COLU_COMM_COM_DATE = "COLU_COMM_COM_DATE";		//	留言日期	DATETIME
	public static final String COLU_COMM_COM_CONT = "COLU_COMM_COM_CONT";		//	留言內容	LONGTEXT
	public static final String COLU_COMM_REP_DATE = "COLU_COMM_REP_DATE";		//	官方回覆日期	DATETIME
	public static final String COLU_COMM_REP_CONT = "COLU_COMM_REP_CONT";		//	官方回覆內容	LONGTEXT
	
	DataSource dataSource;

	public ColumnDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(ArticleComment item) throws SQLException {
		String sql = "insert into " + TABLE_NAME + " (" +
				COLU_ARTI_NO + ", " + USER_NO + ", " + 
				COLU_COMM_COM_DATE + ", " + COLU_COMM_COM_CONT + ", " +
				COLU_COMM_REP_DATE + ", " + COLU_COMM_REP_CONT +
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
				COLU_ARTI_NO + " = ?, " + USER_NO + " = ?, " + 
				COLU_COMM_COM_DATE + " = ?, " + COLU_COMM_COM_CONT + " = ?, " +
				COLU_COMM_REP_DATE + " = ?, " + COLU_COMM_REP_CONT + " = ?" +
				" where " + COLU_COMM_NO + " = ?;";
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
		String sql = "delete from " + TABLE_NAME + " where " + COLU_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<ArticleComment> findAllComment(Integer artId) throws Exception {
		String sql = "select U.NICK_NAME, CC.* from " + TABLE_NAME + " as CC "
				+ "left join USER_NUMB as U on CC.USER_NO = U.USER_NO "
				+ "where " + COLU_ARTI_NO + " = ?;";
		List<ArticleComment> items = new ArrayList<ArticleComment>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setInt(1, artId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArticleComment item = new ArticleComment();
				item.setId(rs.getInt(COLU_COMM_NO));
				item.setArtId(rs.getInt(COLU_ARTI_NO));
				item.setUserId(rs.getString(USER_NO));
				item.setUserNickName(rs.getString("NICK_NAME"));
				Comment comment = new Comment();
				Message message = new Message();
				message.setMessage(rs.getString(COLU_COMM_COM_CONT));
				message.setUpdateDate(rs.getTimestamp(COLU_COMM_COM_DATE));
				comment.setComment(message);
				Message reply = new Message();
				reply.setMessage(rs.getString(COLU_COMM_REP_CONT));
				reply.setUpdateDate(rs.getTimestamp(COLU_COMM_REP_DATE));
				comment.setReply(reply);
				item.setComment(comment);
				items.add(item);
			}
		} 
		return items;
	}
	
	@Override
	public List<ColumnArticle> findAll(String userId) throws SQLException {
		String sql = "select U.NICK_NAME, CA.*, (CASE WHEN M.USER_NO IS NOT NULL THEN true ELSE false END) as isCollection,"
				+ " (select COUNT(*) from COLU_COMM AS CC where CC.COLU_ARTI_NO = CA.COLU_ARTI_NO) as commcount"
				+ " from " + ColumnArticle.TABLE_NAME + " as CA left join"
				+ " (select * from MFCOLA where USER_NO = ?) as M on CA.COLU_ARTI_NO = M.COLU_ARTI_NO "
				+ "left join USER_NUMB as U on CA.USER_NO = U.USER_NO;";
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
				item.setArtColId(rs.getInt(ColumnArticle.ARTI_COLU_NO));
				item.setUserId(rs.getString(ColumnArticle.USER_NO));
				item.setUserNickName(rs.getString("NICK_NAME"));
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
