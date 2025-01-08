package aaron.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.zaxxer.hikari.HikariDataSource;
import aaron.bean.Product;
import static util.JdbcConstant.*;

public class ProductListDaoImpl implements ProductListDao {

	private HikariDataSource ds;

	public ProductListDaoImpl() {
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

	@Override
	public List<Product> selectAll() {
		String sql = "SELECT " + "p.PROD_NO, p.PROD_NAME, p.PROD_INTRO, p.PRICE, p.PROD_STATE, p.PROD_COUNT, "
				+ "b.BRAND_NO, b.BRAND_NAME, b.BRAND_DESC, " + "t.TYPE_NO, t.TYPE_NAME, "
				+ "GROUP_CONCAT(DISTINCT f.FUNT_NAME) AS FUNTS, "
				+ "GROUP_CONCAT(DISTINCT i.INGR_NAME) AS INGREDIENTS, "
				// 從子查詢獲取評價統計
				+ "e_stats.EVAL_COUNT, " + "e_stats.AVG_SCORE " + "FROM PROD p "
				// JOIN 品牌資料
				+ "LEFT JOIN BRAND b ON p.BRAND_NO = b.BRAND_NO "
				// JOIN 產品類型
				+ "LEFT JOIN PTYPE t ON p.TYPE_NO = t.TYPE_NO "
				// JOIN 產品功能關聯表
				+ "LEFT JOIN PROFUN pf ON p.PROD_NO = pf.PROD_NO " + "LEFT JOIN FUNT f ON pf.FUNT_NO = f.FUNT_NO "
				// JOIN 產品成分關聯表
				+ "LEFT JOIN PROING pi ON p.PROD_NO = pi.PROD_NO " + "LEFT JOIN INGR i ON pi.INGR_NO = i.INGR_NO "
				// 子查詢：計算評價統計
				+ "LEFT JOIN ( " + "    SELECT PROD_NO, " + "           COUNT(*) AS EVAL_COUNT, " // 統計評價比數
				+ "           ROUND(IFNULL(AVG(EVAL_LEVEL), 0), 0) AS AVG_SCORE " // 計算平均星星數，無評價時預設為0
				+ "    FROM EVALUATE " + "    GROUP BY PROD_NO " + ") e_stats ON p.PROD_NO = e_stats.PROD_NO "
				+ "GROUP BY p.PROD_NO";

//        String sql = 
//        		"SELECT " +
//                     "p.PROD_NO, p.PROD_NAME, p.PRICE, p.PROD_STATE, p.PROD_COUNT, " +
//                     "b.BRAND_NO, b.BRAND_NAME, b.BRAND_DESC, " +
//                     "t.TYPE_NO, t.TYPE_NAME, " +
//                     "GROUP_CONCAT(DISTINCT f.FUNT_NAME) AS FUNTS, " +
//                     "GROUP_CONCAT(DISTINCT i.INGR_NAME) AS INGREDIENTS, " +
//                     
//                     //這裡使用 count 統計評價比數
//                     "COUNT(e.EVAL_NO) AS EVAL_COUNT, " +
//                     
//                     //這裡 Average 星星數，並 Round，使用 ifnull 避免該產品無評價
//                     "Round(IFNULL(AVG(e.EVAL_LEVEL), 0),0) AS AVG_SCORE " +
//                     
//                     "FROM PROD p " +
//                     "LEFT JOIN BRAND b ON p.BRAND_NO = b.BRAND_NO " +
//                     "LEFT JOIN PTYPE t ON p.TYPE_NO = t.TYPE_NO " +
//                     "LEFT JOIN PROFUN pf ON p.PROD_NO = pf.PROD_NO " +
//                     "LEFT JOIN FUNT f ON pf.FUNT_NO = f.FUNT_NO " +
//                     "LEFT JOIN PROING pi ON p.PROD_NO = pi.PROD_NO " +
//                     "LEFT JOIN INGR i ON pi.INGR_NO = i.INGR_NO " +
//                     
//                     // 這裡 JOIN EVALUATE 讓評價分數要進來
//                     "LEFT JOIN EVALUATE e ON p.PROD_NO = e.PROD_NO " +
//                     "GROUP BY p.PROD_NO";

		List<Product> productList = new ArrayList<>();

		try (Connection conn = ds.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Product product = new Product();

					product.setProdNo(rs.getString("PROD_NO"));
					product.setProdName(rs.getString("PROD_NAME"));
					product.setProdIntro(rs.getString("PROD_INTRO"));

					product.setPrice(rs.getInt("PRICE"));
					product.setProdState(rs.getInt("PROD_STATE"));
					product.setProdCount(rs.getInt("PROD_COUNT"));
					product.setBrandNo(rs.getInt("BRAND_NO"));
					product.setBrandName(rs.getString("BRAND_NAME"));
					product.setBrandDesc(rs.getString("BRAND_DESC"));
					product.setTypeNo(rs.getInt("TYPE_NO"));
					product.setTypeName(rs.getString("TYPE_NAME"));

					product.setFuntName(rs.getString("FUNTS"));
					product.setIngrName(rs.getString("INGREDIENTS"));

					// 評價
					product.setEvalCount(rs.getInt("EVAL_COUNT"));
					product.setAvgScore(rs.getInt("AVG_SCORE"));

					productList.add(product);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return productList;
	}

}
