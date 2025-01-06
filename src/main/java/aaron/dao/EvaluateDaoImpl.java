package aaron.dao;

import static util.JdbcConstant.PASSWORD;
import static util.JdbcConstant.URL;
import static util.JdbcConstant.USER;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariDataSource;
import aaron.bean.Evaluate;

public class EvaluateDaoImpl implements EvaluateDao {

	private HikariDataSource ds;

	public EvaluateDaoImpl() {
		ds = new HikariDataSource();
		ds.setJdbcUrl(URL);
		ds.setUsername(USER);
		ds.setPassword(PASSWORD);
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setMinimumIdle(5);
		ds.setMaximumPoolSize(10);
		ds.addDataSourceProperty("cachePrepStmts", true);
		ds.addDataSourceProperty("prepStmtCacheSize", 250);
		ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
	}

	//查留言
	@Override
	public List<Evaluate> getCommentsByProduct(String prodNo) {
		List<Evaluate> comments = new ArrayList<>();
		
		String sql = "SELECT EVALUATE.EVAL_NO, EVALUATE.USER_NO, EVALUATE.PROD_NO, "
		           + "EVALUATE.EVAL_TITLE, EVALUATE.EVAL_CONTENT, EVALUATE.EVAL_LEVEL, "
		           + "EVALUATE.UPDATE_DATE, USER_NUMB.NICK_NAME "
		           + "FROM EVALUATE "
		           + "JOIN USER_NUMB ON EVALUATE.USER_NO = USER_NUMB.USER_NO "
		           + "WHERE EVALUATE.PROD_NO = ?";


		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, prodNo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Evaluate comment = new Evaluate();

				comment.setEvalNo(rs.getInt("EVAL_NO"));
				comment.setUserNo(rs.getString("USER_NO"));
				comment.setProdNo(rs.getString("PROD_NO"));
				comment.setEvalTitle(rs.getString("EVAL_TITLE"));
				comment.setEvalContent(rs.getString("EVAL_CONTENT"));
				comment.setEvalLevel(rs.getInt("EVAL_LEVEL"));
				comment.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				comment.setNickName(rs.getString("NICK_NAME"));
				
				comments.add(comment);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}

	//新增留言
	@Override
	public boolean addComment(Evaluate comment) {
	    String query = "INSERT INTO EVALUATE (USER_NO, PROD_NO, EVAL_TITLE, EVAL_CONTENT, EVAL_LEVEL, UPDATE_DATE) "
	                 + "VALUES (?, ?, ?, ?, ?, NOW())";

	    try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setString(1, comment.getUserNo());
	        ps.setString(2, comment.getProdNo());
	        ps.setString(3, comment.getEvalTitle());
	        ps.setString(4, comment.getEvalContent());
	        ps.setInt(5, comment.getEvalLevel());
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("新增評價失敗: " + e.getMessage(), e);
	    }
	}


	//刪除
	@Override
	public boolean deleteComment(int evalNo) {
		String query = "DELETE FROM EVALUATE WHERE EVAL_NO = ?";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, evalNo);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//修改
	@Override
	public boolean updateComment(Evaluate comment) {
	    String query = "UPDATE EVALUATE SET EVAL_TITLE = ?, EVAL_CONTENT = ?, EVAL_LEVEL = ?, UPDATE_DATE = NOW() "
	                + "WHERE EVAL_NO = ?";
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setString(1, comment.getEvalTitle());
	        ps.setString(2, comment.getEvalContent());
	        ps.setInt(3, comment.getEvalLevel());
	        ps.setInt(4, comment.getEvalNo());
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public boolean isOwner(String userNo, int evalNo) {
        String query = "SELECT * FROM EVALUATE WHERE USER_NO = ? AND EVAL_NO = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userNo);
            ps.setInt(2, evalNo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	//檢舉
	@Override
	public boolean addReport(Evaluate report) {
		String query = "INSERT INTO EVWH (EVAL_NO, PROC_NO, WHICONTENT, WHIDATE) " + "VALUES (?, ?, ?, NOW())";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, report.getEvalNo());
			ps.setInt(2, report.getProcNo());
			ps.setString(3, report.getWhiContent());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
