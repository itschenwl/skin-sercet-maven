package aaron.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import aaron.bean.Product;
import aaron.dao.ProductDetailDaoImpl;

//http://127.0.0.1:8080/skin-sercet-maven/product/detail?prodid=P00000002

@WebServlet("/product/detail")
public class ProductDetailServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ProductDetailDaoImpl productDaoImpl = new ProductDetailDaoImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 請求
		
		// Gson 實例，轉成 JSON 格式
		
	    Gson gson = new Gson();
		
	    String prodNo = req.getParameter("prodid");

	    // 回應
	    
	    // 將物件轉為 JSON 字串
	    resp.setContentType("application/json; charset=UTF-8");

	    Product product = productDaoImpl.selectByProdNo(prodNo);
	    resp.getWriter().write(gson.toJson(product));
	}
}
