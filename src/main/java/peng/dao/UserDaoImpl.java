package peng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import peng.javabean.User;
import util.ServiceLocator;

@SuppressWarnings("rawtypes")
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
					Timestamp birthday = rs.getTimestamp(User.BIRTHDAY);
					User user = new User(
					rs.getString(User.USER_NO), // USER_NO 不允許 NULL
			        rs.getString(User.FULL_NAME) != null ? rs.getString(User.FULL_NAME) : "", // FULL_NAME 允許 NULL，處理為空字串
			        rs.getString(User.USER_PASSWORD), // USER_PASSWORD 不允許 NULL
			        rs.getString(User.NICK_NAME) != null ? rs.getString(User.NICK_NAME) : "", // NICK_NAME 允許 NULL，處理為空字串
			        rs.getString(User.TEL_NUMBER) != null ? rs.getString(User.TEL_NUMBER) : "", // TEL_NUMBER 允許 NULL，處理為空字串
			        birthday.toString(), // BIRTHDAY 允許 NULL，不需要處理為 ""，直接使用 null
			        rs.getInt(User.SEX) != 0 ? rs.getInt(User.SEX) : null, // SEX 允許 NULL，處理為 null
//			        rs.getInt(User.MEM_LEVEL), // MEM_LEVEL 預設值 0，不為 NULL
			        rs.getString(User.EMAIL) != null ? rs.getString(User.EMAIL) : "", // EMAIL 允許 NULL，處理為空字串
			        rs.getString(User.IG) != null ? rs.getString(User.IG) : "", // IG 允許 NULL，處理為空字串
//			        rs.getInt(User.STATE), // STATE 預設值 1，不為 NULL
//			        rs.getBoolean(User.PASS), // PASS 預設值 false，不為 NULL
			        rs.getString(User.PERSONAL_INFO) != null ? rs.getString(User.PERSONAL_INFO) : ""
//			        rs.getString(User.AVATAR) != null ? rs.getString(User.AVATAR) : ""
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
	
	
//	@Override
//	public void IntroUpdate(User user) throws SQLException {
//		String sql = "update "+ User.TABLE_NAME 
//					+ "set " + User.IG 
//					+ " = ?, " + User.PERSONAL_INFO 
//					+" = ? where " + User.USER_NO +" = ?;";
//		try (Connection connection = dataSource.getConnection();
//				PreparedStatement ps = connection.prepareStatement(sql);) {
//			ps.setString(1, user.getIg());
//			ps.setString(2, user.getPersonalInfo());
//			ps.setString(3, user.getUserNo());
//			ps.executeUpdate();
//		}
//	}
	
	@Override
	public int update(User user) throws SQLException {
		String sql = "update "+ User.TABLE_NAME + " set ";
//					+ User.AVATAR + " = ?, " 
		int index = 1;
		if (user.getNickname() != null) {
			sql += User.NICK_NAME + " = ?";
			index++;
		}
		if (user.getName() != null) {
			if (index > 1) {
				sql += ", ";
			}
			sql += User.FULL_NAME + " = ?";
			index++;
		}
		if (user.getSex() != null) {
			if (index > 1) {
				sql += ", ";
			}
			sql += User.SEX + " = ?";
			index++;
		}
		if (user.getTelNumber() != null) {
			if (index > 1) {
				sql += ", ";
			}
			sql += User.TEL_NUMBER + " = ?";
			index++;
		}
		if (user.getEmail() != null) {
			if (index > 1) {
				sql += ", ";
			}
			sql += User.EMAIL + " = ?";
			index++;
		}
		if (user.getBirthday() != null) {
			if (index > 1) {
				sql += ", ";
			}
			sql += User.BIRTHDAY + " = ?";
			index++;
		}
		if (user.getPassword() != null) {
			if (index > 1) {
				sql += ", ";
			}
			sql += User.USER_PASSWORD + " = ?";
			index++;
		}
		if (user.getPersonalInfo() != null) {
			if (index > 1) {
				sql += ", ";
			}
			sql += User.PERSONAL_INFO + " = ?" ;
			index++;
		}
		if (user.getIg() != null) {
			if (index > 1) {
				sql += ", ";
			}
			sql += User.IG + " = ?";
		}
		
		sql += " where " + User.USER_NO +" = ?;";
		System.out.println("update sql: " + sql);
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			index = 1;
			if (user.getNickname() != null) {
				ps.setString(index++, user.getNickname());
			}
			if (user.getName() != null) {
				ps.setString(index++, user.getName());
			}
			if (user.getSex() != null) {
				ps.setInt(index++, user.getSex());
			}
			if (user.getTelNumber() != null) {
				ps.setString(index++, user.getTelNumber());
			}
			if (user.getEmail() != null) {
				ps.setString(index++, user.getEmail());
			}
			if (user.getBirthday() != null) {
				try {
				    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				    Date parsedDate = dateFormat.parse(user.getBirthday());
				    Timestamp birthday = new Timestamp(parsedDate.getTime());
					ps.setTimestamp(index++, birthday);
				} catch (Exception e) {
					System.out.println("update: " + e.toString());
				}
			}
			if (user.getPassword() != null) {
				ps.setString(index++, user.getPassword());
			}
			if (user.getPersonalInfo() != null) {
				ps.setString(index++, user.getPersonalInfo());
			}
			if (user.getIg() != null) {
				ps.setString(index++, user.getIg());
			}
			ps.setString(index++, user.getUserNo());
			if (index > 1) {
				return ps.executeUpdate();
			} else {
				return -1;
			}
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
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Timestamp birthday = rs.getTimestamp(User.BIRTHDAY);
				String birthDayStr = null;
				if (birthday != null) {
					birthDayStr = dateFormat.format(birthday.getTime());
				}
				userReturn = new User(
						rs.getString(User.USER_NO), // USER_NO 不允許 NULL
				        rs.getString(User.FULL_NAME) != null ? rs.getString(User.FULL_NAME) : "", // FULL_NAME 允許 NULL，處理為空字串
				        rs.getString(User.USER_PASSWORD), // USER_PASSWORD 不允許 NULL
				        rs.getString(User.NICK_NAME) != null ? rs.getString(User.NICK_NAME) : "", // NICK_NAME 允許 NULL，處理為空字串
				        rs.getString(User.TEL_NUMBER) != null ? rs.getString(User.TEL_NUMBER) : "", // TEL_NUMBER 允許 NULL，處理為空字串
				        birthDayStr, // BIRTHDAY 允許 NULL，不需要處理為 ""，直接使用 null
				        rs.getInt(User.SEX) != 0 ? rs.getInt(User.SEX) : null, // SEX 允許 NULL，處理為 null
//				        rs.getInt(User.MEM_LEVEL), // MEM_LEVEL 預設值 0，不為 NULL
				        rs.getString(User.EMAIL) != null ? rs.getString(User.EMAIL) : "", // EMAIL 允許 NULL，處理為空字串
				        rs.getString(User.IG) != null ? rs.getString(User.IG) : "", // IG 允許 NULL，處理為空字串
//				        rs.getInt(User.STATE), // STATE 預設值 1，不為 NULL
//				        rs.getBoolean(User.PASS), // PASS 預設值 false，不為 NULL
				        rs.getString(User.PERSONAL_INFO) != null ? rs.getString(User.PERSONAL_INFO) : ""
//				        rs.getString(User.AVATAR) != null ? rs.getString(User.AVATAR) : ""
				    );
			}
		} 
		return userReturn;
	}

	@Override
	public User findUser(String userNo) throws SQLException {
		User userReturn = null;
		String sql = "select * from " + User.TABLE_NAME +
				" where " + User.USER_NO + " = ?;";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, userNo);
			ResultSet rs = ps.executeQuery();
			// 如果輸入帳密正確，回傳userInfo
			if (rs.next()) {
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Timestamp birthday = rs.getTimestamp(User.BIRTHDAY);
				String birthDayStr = null;
				if (birthday != null) {
					birthDayStr = dateFormat.format(birthday.getTime());
				}
				userReturn = new User(
						rs.getString(User.USER_NO), // USER_NO 不允許 NULL
				        rs.getString(User.FULL_NAME) != null ? rs.getString(User.FULL_NAME) : "", // FULL_NAME 允許 NULL，處理為空字串
				        rs.getString(User.USER_PASSWORD), // USER_PASSWORD 不允許 NULL
				        rs.getString(User.NICK_NAME) != null ? rs.getString(User.NICK_NAME) : "", // NICK_NAME 允許 NULL，處理為空字串
				        rs.getString(User.TEL_NUMBER) != null ? rs.getString(User.TEL_NUMBER) : "", // TEL_NUMBER 允許 NULL，處理為空字串
				        birthDayStr, // BIRTHDAY 允許 NULL，不需要處理為 ""，直接使用 null
				        rs.getInt(User.SEX) != 0 ? rs.getInt(User.SEX) : null, // SEX 允許 NULL，處理為 null
//				        rs.getInt(User.MEM_LEVEL), // MEM_LEVEL 預設值 0，不為 NULL
				        rs.getString(User.EMAIL) != null ? rs.getString(User.EMAIL) : "", // EMAIL 允許 NULL，處理為空字串
				        rs.getString(User.IG) != null ? rs.getString(User.IG) : "", // IG 允許 NULL，處理為空字串
//				        rs.getInt(User.STATE), // STATE 預設值 1，不為 NULL
//				        rs.getBoolean(User.PASS), // PASS 預設值 false，不為 NULL
				        rs.getString(User.PERSONAL_INFO) != null ? rs.getString(User.PERSONAL_INFO) : ""
//				        rs.getString(User.AVATAR) != null ? rs.getString(User.AVATAR) : ""
				    );
			}
		} 
		return userReturn;
	}
}
