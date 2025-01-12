package peng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import peng.javabean.User;
import util.ServiceLocator;

public class UserDaoImpl implements UserDao{
	DataSource dataSource;

	public UserDaoImpl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}
	
	public User selectByUsername(String userNo) {
		String sql = "select * from " + User.TABLE_NAME + " where " + User.USER_NO + " = ?;";
		try (
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)
		){
			ps.setString(1, userNo);
			try (ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					User user = new User(
					rs.getString(User.USER_NO), // USER_NO 不允許 NULL
			        rs.getString(User.FULL_NAME) != null ? rs.getString(User.FULL_NAME) : "", // FULL_NAME 允許 NULL，處理為空字串
			        rs.getString(User.USER_PASSWORD), // USER_PASSWORD 不允許 NULL
			        rs.getString(User.NICK_NAME) != null ? rs.getString(User.NICK_NAME) : "", // NICK_NAME 允許 NULL，處理為空字串
			        rs.getString(User.TEL_NUMBER) != null ? rs.getString(User.TEL_NUMBER) : "", // TEL_NUMBER 允許 NULL，處理為空字串
			        rs.getTimestamp(User.BIRTHDAY), // BIRTHDAY 允許 NULL，不需要處理為 ""，直接使用 null
			        rs.getInt(User.SEX) != 0 ? rs.getInt(User.SEX) : null, // SEX 允許 NULL，處理為 null
			        rs.getInt(User.MEM_LEVEL), // MEM_LEVEL 預設值 0，不為 NULL
			        rs.getString(User.EMAIL) != null ? rs.getString(User.EMAIL) : "", // EMAIL 允許 NULL，處理為空字串
			        rs.getString(User.IG) != null ? rs.getString(User.IG) : "", // IG 允許 NULL，處理為空字串
			        rs.getInt(User.STATE), // STATE 預設值 1，不為 NULL
			        rs.getBoolean(User.PASS), // PASS 預設值 false，不為 NULL
			        rs.getString(User.PERSONAL_INFO) != null ? rs.getString(User.PERSONAL_INFO) : "",
			        rs.getString(User.AVATAR) != null ? rs.getString(User.AVATAR) : ""
					);
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String register(User user) throws SQLException {
		String userNo = user.getUserNo();
		if (userNo == null || userNo.length() < 1 || userNo.length() > 20) {
			return "使用者名稱長度不正確";			
		}
		
		String password = user.getPassword();
		if (password == null || password.length() < 4 || password.length() > 16) {
			return "密碼長度不正確";			
		}
		UserDaoImpl userDaoImpl = new UserDaoImpl();
		//檢查使用者名稱/是否被註冊
		if (userDaoImpl.selectByUsername(userNo) != null) {
			return "此名稱已被註冊";
		}
		
		int count = userDaoImpl.insert(user);
		if (count > 0) {
			return null;
		} else {
			return "註冊失敗";
		}
	}
	
	@Override
	public int insert(User user) throws SQLException {
		String sql = "insert into " + User.TABLE_NAME + " (" +
			User.USER_NO + ", " + User.USER_PASSWORD + ") values(?, ?);";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, user.getUserNo());
			ps.setString(2, user.getPassword());
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	@Override
	public void IntroUpdate(User user) throws SQLException {
		String sql = "update "+ User.TABLE_NAME 
					+ "set " + User.IG 
					+ " = ?, " + User.PERSONAL_INFO 
					+" = ? where " + User.USER_NO +" = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, user.getIg());
			ps.setString(2, user.getPersonalInfo());
			ps.setString(3, user.getUserNo());
			ps.executeUpdate();
		}
	}
	
	@Override
	public void update(User user) throws SQLException {
		String sql = "update "+ User.TABLE_NAME 
					+ "set " + User.AVATAR 
					+ " = ?, " + User.NICK_NAME 
					+ " = ?, " + User.FULL_NAME 
					+ " = ?, " + User.SEX 
					+ " = ?, " + User.TEL_NUMBER 
					+ " = ?, " + User.EMAIL 
					+ " = ?, " + User.BIRTHDAY 
					+ " = ?, " + User.USER_PASSWORD 
					+" = ? where " + User.USER_NO +" = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, user.getAvatar());
			ps.setString(2, user.getNickname());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getSex());
			ps.setString(5, user.getTelNumber());
			ps.setString(6, user.getEmail());
			ps.setTimestamp(7, user.getBirthday());
			ps.setString(8, user.getPassword());
			ps.setString(9, user.getUserNo());
			ps.executeUpdate();
		}
	}
	
	@Override
	public User validate(String userNo, String password) throws SQLException {
		User userReturn = null;
		String sql = "select * from " + User.TABLE_NAME +
				" where " + User.USER_NO + " = ? and " + User.USER_PASSWORD +
				" = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, userNo);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			// 如果輸入帳密正確，回傳userInfo
			if (rs.next()) {
				userReturn = new User(
						rs.getString(User.USER_NO), // USER_NO 不允許 NULL
				        rs.getString(User.FULL_NAME) != null ? rs.getString(User.FULL_NAME) : "", // FULL_NAME 允許 NULL，處理為空字串
				        rs.getString(User.USER_PASSWORD), // USER_PASSWORD 不允許 NULL
				        rs.getString(User.NICK_NAME) != null ? rs.getString(User.NICK_NAME) : "", // NICK_NAME 允許 NULL，處理為空字串
				        rs.getString(User.TEL_NUMBER) != null ? rs.getString(User.TEL_NUMBER) : "", // TEL_NUMBER 允許 NULL，處理為空字串
				        rs.getTimestamp(User.BIRTHDAY), // BIRTHDAY 允許 NULL，不需要處理為 ""，直接使用 null
				        rs.getInt(User.SEX) != 0 ? rs.getInt(User.SEX) : null, // SEX 允許 NULL，處理為 null
				        rs.getInt(User.MEM_LEVEL), // MEM_LEVEL 預設值 0，不為 NULL
				        rs.getString(User.EMAIL) != null ? rs.getString(User.EMAIL) : "", // EMAIL 允許 NULL，處理為空字串
				        rs.getString(User.IG) != null ? rs.getString(User.IG) : "", // IG 允許 NULL，處理為空字串
				        rs.getInt(User.STATE), // STATE 預設值 1，不為 NULL
				        rs.getBoolean(User.PASS), // PASS 預設值 false，不為 NULL
				        rs.getString(User.PERSONAL_INFO) != null ? rs.getString(User.PERSONAL_INFO) : "",
				        rs.getString(User.AVATAR) != null ? rs.getString(User.AVATAR) : ""
				    );
			}
		} 
		return userReturn;
	}
}
