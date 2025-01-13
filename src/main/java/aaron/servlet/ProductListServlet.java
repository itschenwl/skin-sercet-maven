package aaron.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import aaron.bean.Product;
import aaron.dao.ProductListDaoImpl;

@WebServlet("/product")
public class ProductListServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    private ProductListDaoImpl productDaoImpl = new ProductListDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	System.out.println("doGet");
    	Gson gson = new Gson();

        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String userId = req.getParameter("userId");
        if (userId != null && !userId.isEmpty()) {
	        // 查詢所有產品
	        List<Product> products = productDaoImpl.selectAll(userId);
	
	        String json = gson.toJson(products);
	        resp.getWriter().write(json);
        }
    }
}
