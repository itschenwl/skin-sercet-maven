package andy.dao.activity;

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
import andy.javabean.activity.Activity;
import andy.javabean.activity.ActivityComment;
import andy.javabean.activity.ActivityJoin;
import andy.javabean.column.ColumnArticle;
import andy.javabean.column.ColumnComment;
import util.ServiceLocator;

public class ActivityDaoImpl implements ActivityDao {

	DataSource dataSource;

	public ActivityDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(ActivityComment item) throws SQLException {
		String sql = "insert into " + ActivityComment.TABLE_NAME + " (" +
				ActivityComment.ACTI_NO + ", " + ActivityComment.USER_NO + ", " + 
				ActivityComment.COM_DAT + ", " + ActivityComment.COM_CON + ", " +
				ActivityComment.REP_DATE + ", " + ActivityComment.REP_CON +
			") values(?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getActId());
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
	public int update(ActivityComment item) throws SQLException {
		String sql = "update " + ActivityComment.TABLE_NAME + " set " + 
				ActivityComment.ACTI_NO + " = ?, " + ActivityComment.USER_NO + " = ?, " + 
				ActivityComment.COM_DAT + " = ?, " + ActivityComment.COM_CON + " = ?, " +
				ActivityComment.REP_DATE + " = ?, " + ActivityComment.REP_CON + " = ?" +
				" where " + ActivityComment.ACTI_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, item.getActId());
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
		String sql = "delete from " + ActivityComment.TABLE_NAME + " where " + ActivityComment.ACTI_COMM_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
	}

	@Override
	public List<ActivityComment> findAllComment(Integer actId) throws Exception {
		String sql = "select * from " + ActivityComment.TABLE_NAME + " where " + ActivityComment.ACTI_COMM_NO + " = ?;";
		List<ActivityComment> items = new ArrayList<ActivityComment>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ps.setInt(1, actId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ActivityComment item = new ActivityComment();
				item.setId(rs.getInt(ActivityComment.ACTI_COMM_NO));
				item.setActId(rs.getInt(ActivityComment.ACTI_NO));
				item.setUserId(rs.getString(ActivityComment.USER_NO));
				Comment comment = new Comment();
				Message message = new Message();
				message.setMessage(rs.getString(ActivityComment.COM_DAT));
				message.setUpdateDate(rs.getTimestamp(ActivityComment.COM_CON));
				comment.setComment(message);
				Message reply = new Message();
				reply.setMessage(rs.getString(ActivityComment.REP_DATE));
				reply.setUpdateDate(rs.getTimestamp(ActivityComment.REP_CON));
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
	public List<Activity> findAllJoin(String userId) throws Exception {
		String sql = "select AJ.STATE as regState, AJ.REG_DATE,\r\n"
				+ " (CASE WHEN M.USER_NO IS NULL THEN false ELSE true END) as isCollection,"
				+ " (select COUNT(*) from ACTI_COMM AS AC where AC.ACTI_NO = A.ACTI_NO) as commcount,"
				+ " A.*"
				+ " from ACTI_JOIN as AJ left join ACTI as A on AJ.ACTI_NO = A.ACTI_NO left join MFACT as M on AJ.ACTI_NO = M.ACTI_NO"
				+ " where AJ.USER_NO = ?;";
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
				item.setIsCollection(rs.getBoolean("isCollection"));
				item.setCollectionCount((int)(Math.random()*100));
				items.add(item);
			}
		} 
		return items;
	}
	
	@Override
	public List<Activity> findAll(String userId) throws SQLException {
		String sql = "select A.*, (CASE WHEN M.USER_NO = ? THEN true ELSE false END) as isFavorite ," + 
				" (select COUNT(*) from ACTI_COMM AS AC where AC.ACTI_NO = A.ACTI_NO) as commcount" + 
				" from " + Activity.TABLE_NAME + " as A left join MFACT as M on A.ACTI_NO = M.ACTI_NO;";
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
				item.setIsFavorite(rs.getBoolean("isFavorite"));
				item.setFavoriteCount((int)(Math.random()*100));
				item.setIsComment(false);
				item.setCommentCount(rs.getInt("commcount"));
				item.setIsCollection(false);
				item.setCollectionCount((int)(Math.random()*100));
				items.add(item);
			}
		} 
		return items;
	}
}
