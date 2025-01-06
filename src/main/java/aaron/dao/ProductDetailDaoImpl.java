package aaron.dao;

import static util.JdbcConstant.PASSWORD;
import static util.JdbcConstant.URL;
import static util.JdbcConstant.USER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.zaxxer.hikari.HikariDataSource;
import aaron.bean.Product;

public class ProductDetailDaoImpl implements ProductDetailDao {

	private HikariDataSource ds;

	public ProductDetailDaoImpl() {
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
	public Product selectByProdNo(String prodNo) {
		String sql = "SELECT " + "p.PROD_NO, p.PROD_NAME, p.PROD_INTRO, p.PRICE, p.PROD_STATE, p.PROD_COUNT, "
				+ "b.BRAND_NO, b.BRAND_NAME, b.BRAND_DESC, " + "t.TYPE_NO, t.TYPE_NAME, "
				+ "img.PI_NO, img.PI_NAME, img.PI_DATA, img.PI_URL, img.PI_UPDATE " + "FROM PROD p "
				+ "LEFT JOIN BRAND b ON p.BRAND_NO = b.BRAND_NO " + "LEFT JOIN PTYPE t ON p.TYPE_NO = t.TYPE_NO "
				+ "LEFT JOIN PROIMG img ON p.PROD_NO = img.PROD_NO " + "WHERE p.PROD_NO = ?";

		String profunSql = "SELECT f.FUNT_NO, f.FUNT_NAME " + "FROM PROFUN pf "
				+ "INNER JOIN FUNT f ON pf.FUNT_NO = f.FUNT_NO " + "WHERE pf.PROD_NO = ?";

		String proingSql = "SELECT i.INGR_NO, i.INGR_NAME, i.INGR_DESC, i.ALLERGY " + "FROM PROING pi "
				+ "INNER JOIN INGR i ON pi.INGR_NO = i.INGR_NO " + "WHERE pi.PROD_NO = ?";

		Product product = null;

		try (Connection conn = ds.getConnection()) {
		    // 主表查詢
		    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        pstmt.setString(1, prodNo);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                product = new Product();
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
		                product.setPiNo(rs.getInt("PI_NO"));
		                product.setPiName(rs.getString("PI_NAME"));
		                product.setPiData(rs.getBytes("PI_DATA"));
		                product.setPiUrl(rs.getString("PI_URL"));
		                product.setPiUpdate(rs.getTimestamp("PI_UPDATE"));
		            }
		        }
		    }

		    // PROFUN 功效
		    try (PreparedStatement pstmt = conn.prepareStatement(profunSql)) {
		        pstmt.setString(1, prodNo);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            product.setFuntNames(new ArrayList<>());
		            while (rs.next()) {
		                product.getFuntNames().add(rs.getString("FUNT_NAME"));
		            }
		        }
		    }

		    // PROING 成份
		    try (PreparedStatement pstmt = conn.prepareStatement(proingSql)) {
		        pstmt.setString(1, prodNo);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            product.setIngrNames(new ArrayList<>());
		            product.setAllergy(new ArrayList<>());

		            while (rs.next()) {
		                product.getIngrNames().add(rs.getString("INGR_NAME"));
		                product.getAllergy().add(rs.getString("Allergy"));

		            }
		        }
		    }
		}

		
		catch (

		Exception e) {
			e.printStackTrace();
		}

		return product;
	}
}
