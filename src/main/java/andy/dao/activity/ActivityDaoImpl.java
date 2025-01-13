package andy.dao.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import andy.javabean.ArticleComment;
import andy.javabean.Comment;
import andy.javabean.Message;
import andy.javabean.activity.Activity;
import andy.javabean.activity.ActivityJoin;
import util.ServiceLocator;

public class ActivityDaoImpl implements ActivityDao {
	public static final String TABLE_NAME = "ACTI_COMM";
	public static final String ACTI_COMM_NO = "ACTI_COMM_NO";		//	活動留言編號	INT
	public static final String ACTI_NO = "ACTI_NO";		//	活動編號	INT
	public static final String USER_NO = "USER_NO";		//	用戶編號	CHAR
	public static final String COM_DATE = "COM_DAT";		//	留言日期	DATETIME
	public static final String COM_CON = "COM_CON";		//	留言內容	LONGTEXT
	public static final String REP_DATE = "REP_DATE";		//	官方回覆日期	DATETIME
	public static final String REP_CON = "REP_CON";		//	官方回覆內容	LONGTEXT
	
	DataSource dataSource;

	public ActivityDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(ArticleComment item) throws SQLException {
		String sql = "insert into " + TABLE_NAME + " (" +
				ACTI_NO + ", " + USER_NO + ", " + 
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
				ACTI_NO + " = ?, " + USER_NO + " = ?, " + 
				COM_DATE + " = ?, " + COM_CON + " = ?, " +
				REP_DATE + " = ?, " + REP_CON + " = ?" +
				" where " + ACTI_COMM_NO + " = ?;";
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
		String sql = "delete from " + TABLE_NAME + " where " + ACTI_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<ArticleComment> findAllComment(Integer actId) throws Exception {
		String sql = "select * from " + TABLE_NAME + " where " + ACTI_NO + " = ?;";
		List<ArticleComment> items = new ArrayList<ArticleComment>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setInt(1, actId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArticleComment item = new ArticleComment();
				item.setId(rs.getInt(ACTI_COMM_NO));
				item.setArtId(rs.getInt(ACTI_NO));
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
	public int insert(ActivityJoin item) throws Exception {
		String sql = "insert into " + ActivityJoin.TABLE_NAME + " (" +
				ActivityJoin.ACTI_NO + ", " + ActivityJoin.USER_NO + ", " + 
				ActivityJoin.STATE + ", " + ActivityJoin.REG_DATE + 
			") values(?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getActId());
			ps.setString(2, item.getUserId());
			ps.setInt(3, item.getState());
			ps.setTimestamp(4, item.getRegisterDate());
			return ps.executeUpdate();
		}
	}

	@Override
	public int deleteJoin(Integer id, String userId) throws Exception {
		String sql = "delete from " + ActivityJoin.TABLE_NAME +
				" where " + ActivityJoin.ACTI_NO + " = ? AND " + ActivityJoin.USER_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.setString(2, userId);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<Activity> findAll(String userId) throws SQLException {
		String sql = "select (CASE WHEN AJ.STATE IS NULL THEN -1 ELSE AJ.STATE END) as joinState,"
				+ " A.*, (CASE WHEN M.USER_NO IS NOT NULL THEN true ELSE false END) as isCollection ," + 
				" (select COUNT(*) from ACTI_COMM AS AC where AC.ACTI_NO = A.ACTI_NO) as commcount" + 
				" from " + Activity.TABLE_NAME + " as A left join "
				+ " (select * from MFACT where USER_NO = ?) as M on A.ACTI_NO = M.ACTI_NO"
				+ " left join ACTI_JOIN AS AJ on AJ.ACTI_NO = A.ACTI_NO AND AJ.USER_NO = ?;";
		List<Activity> items = new ArrayList<Activity>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setString(1, userId);
			ps.setString(2, userId);
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
				item.setJoinState(rs.getInt("joinState"));
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
