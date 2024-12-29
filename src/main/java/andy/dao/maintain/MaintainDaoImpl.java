package andy.dao.maintain;

import static util.JdbcConstant.PASSWORD;
import static util.JdbcConstant.URL;
import static util.JdbcConstant.USER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import andy.javabean.maintain.Maintain;

public class MaintainDaoImpl implements MaintainDao {

	private HikariDataSource ds;
	
	public MaintainDaoImpl() {
		ds = new HikariDataSource();
		ds.setJdbcUrl(URL);
		ds.setUsername(USER);
		ds.setPassword(PASSWORD);
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		// 最小空閒連線數
		ds.setMinimumIdle(5);
		// 最大連線數
		ds.setMaximumPoolSize(10);
		// 啟⽤預編譯敘述快取
		ds.addDataSourceProperty("cachePrepStmts", true);
		// 設定最多可保存的預編譯敘述數量
		ds.addDataSourceProperty("prepStmtCacheSize", 250);
		// 設定預編譯敘述⻑度上限
		ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
	}

	@Override
	public Maintain getLast() {
		String sql = "SELECT * FROM remind ORDER BY REMI_ID DESC LIMIT 1";
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					Maintain maintain = new Maintain();
					maintain.setId(rs.getInt("REMI_ID"));
					maintain.setUserId(rs.getString("USER_NO"));
					maintain.setTitle(rs.getString("CONTENT"));
					maintain.setInterval(rs.getLong("HOUR_INTER"));
					maintain.setReminder(rs.getTimestamp("REMI_DATE"));
					return maintain;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Maintain selectById(Integer id) {
		String sql = "select * from remind where REMI_ID = ?";
		try (Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					Maintain maintain = new Maintain();
					maintain.setId(rs.getInt("REMI_ID"));
					maintain.setUserId(rs.getString("USER_NO"));
					maintain.setTitle(rs.getString("CONTENT"));
					maintain.setInterval(rs.getLong("HOUR_INTER"));
					maintain.setReminder(rs.getTimestamp("REMI_DATE"));
					return maintain;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insert(Maintain maintain) {
		String sql = "insert into remind (USER_NO, CONTENT, HOUR_INTER, REMI_DATE) values(?, ?, ?, ?)";
		try (
				Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			) {
			pstmt.setString(1, maintain.getUserId());
			pstmt.setString(2, maintain.getTitle());
			pstmt.setLong(3, maintain.getInterval());
			pstmt.setTimestamp(4, maintain.getReminder());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteById(Integer id) {
		String sql = "delete from remind where REMI_ID = ?";
		try (
				Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			) {
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int update(Maintain maintain) {
		String sql = "update remind set USER_NO=?, CONTENT=?, HOUR_INTER=?, REMI_DATE=? where REMI_ID = ?";
		try (
				Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			) {
			pstmt.setString(1, maintain.getUserId());
			pstmt.setString(2, maintain.getTitle());
			pstmt.setLong(3, maintain.getInterval());
			pstmt.setTimestamp(4, maintain.getReminder());
			pstmt.setInt(5, maintain.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<Maintain> selectAll() {
		String sql = "select * from remind";
		try (
				Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
			) {
			List<Maintain> maintains = new ArrayList<>();
			while (rs.next()) {
				Maintain maintain = new Maintain();
				maintain.setId(rs.getInt("REMI_ID"));
				maintain.setUserId(rs.getString("USER_NO"));
				maintain.setTitle(rs.getString("CONTENT"));
				maintain.setInterval(rs.getLong("HOUR_INTER"));
				maintain.setReminder(rs.getTimestamp("REMI_DATE"));
				maintains.add(maintain);
			}
			return maintains;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
