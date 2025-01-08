package andy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import andy.javabean.user.Feedback;
import util.Dao;
import util.ServiceLocator;

public class FeedbackDaoImpl implements Dao<Feedback> {
	DataSource dataSource;

	public FeedbackDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public int insert(Feedback feedback) throws SQLException {
		String sql = "insert into " + Feedback.TABLE_NAME + " (" +
			Feedback.USER_NO + ", " + Feedback.PROC_NO + ", " + 
			Feedback.CONTENT + ", " + Feedback.REPLY + ", " +
			Feedback.UO_DATE + ", " + Feedback.UO_REPDATE +
			") values(?, ?, ?, ?, ?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, feedback.getUserId());
			ps.setInt(2, feedback.getProcId());
			ps.setString(3, feedback.getContent());
			ps.setString(4, feedback.getReply());
			ps.setTimestamp(5, feedback.getCreateDate());
			ps.setTimestamp(6, feedback.getReplyDate());
			return ps.executeUpdate();
		}
	}

	@Override
	public int update(Feedback feedback) throws SQLException {
		String sql = "update " + Feedback.TABLE_NAME + " set " + 
				Feedback.USER_NO + " = ?, " + Feedback.PROC_NO + " = ?, " + 
				Feedback.CONTENT + " = ?, " + Feedback.REPLY + " = ?, " +
				Feedback.UO_DATE + " = ?, " + Feedback.UO_REPDATE + " = ?" +
				" where " + Feedback.UO_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, feedback.getUserId());
			ps.setInt(2, feedback.getProcId());
			ps.setString(3, feedback.getContent());
			ps.setString(4, feedback.getReply());
			ps.setTimestamp(5, feedback.getCreateDate());
			ps.setTimestamp(6, feedback.getReplyDate());
			ps.setInt(7, feedback.getId());
			return ps.executeUpdate();
		}
	}

	@Override
	public int deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Integer id) throws SQLException {
		String sql = "delete from " + Feedback.TABLE_NAME + " where " + Feedback.UO_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			return ps.executeUpdate();
		}
	}

	@Override
	public Feedback findById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Feedback findById(Integer id) throws SQLException {
		String sql = "select * from " + Feedback.TABLE_NAME + " where " + Feedback.UO_NO + " = ?;";
		Feedback feedback = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setInt(1, id);
			/*
			 * 當Statement關閉，ResultSet也會自動關閉， 可以不需要將ResultSet宣告置入try with
			 * resources小括號內，參看ResultSet說明
			 */
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				feedback = new Feedback();
				feedback.setId(rs.getInt(Feedback.UO_NO));
				feedback.setUserId(rs.getString(Feedback.USER_NO));
				feedback.setProcId(rs.getInt(Feedback.PROC_NO));
				feedback.setContent(rs.getString(Feedback.CONTENT));
				feedback.setReply(rs.getString(Feedback.REPLY));
				feedback.setCreateDate(rs.getTimestamp(Feedback.UO_DATE));
				feedback.setReplyDate(rs.getTimestamp(Feedback.UO_REPDATE));
			}
		}
		return feedback;
	}

	@Override
	public List<Feedback> findAll() throws SQLException {
		String sql = "select * from " + Feedback.TABLE_NAME + ";";
		List<Feedback> items = new ArrayList<Feedback>();
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Feedback feedback = new Feedback();
				feedback.setId(rs.getInt(Feedback.UO_NO));
				feedback.setUserId(rs.getString(Feedback.USER_NO));
				feedback.setProcId(rs.getInt(Feedback.PROC_NO));
				feedback.setContent(rs.getString(Feedback.CONTENT));
				feedback.setReply(rs.getString(Feedback.REPLY));
				feedback.setCreateDate(rs.getTimestamp(Feedback.UO_DATE));
				feedback.setReplyDate(rs.getTimestamp(Feedback.UO_REPDATE));
				items.add(feedback);
			}
		} 
		return items;
	}

}
