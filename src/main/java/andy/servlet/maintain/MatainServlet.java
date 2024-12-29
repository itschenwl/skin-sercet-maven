package andy.servlet.maintain;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import andy.dao.maintain.MaintainDaoImpl;
import andy.javabean.maintain.Maintain;

@WebServlet("/user/maintains")
public class MatainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MaintainDaoImpl maintaionDaoImpl = new MaintainDaoImpl();
	private Gson gson = new GsonBuilder()
			.setDateFormat("yyyy/MM/dd HH:mm:ss")
			.create();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 查詢
		String id = req.getParameter("id");
		//resp.setContentType("application/json; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		if (id == null || id.isEmpty()) {
			List<Maintain> maintains = maintaionDaoImpl.selectAll();
			resp.getWriter().write(gson.toJson(maintains));
		} else {
			Maintain maintain = maintaionDaoImpl.selectById(Integer.parseInt(id));
			resp.getWriter().write(gson.toJson(maintain));
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 新增
		req.setCharacterEncoding("UTF-8");
		Maintain maintain = gson.fromJson(req.getReader(), Maintain.class);
		maintaionDaoImpl.insert(maintain);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 修改
		req.setCharacterEncoding("UTF-8");
		Maintain maintain = gson.fromJson(req.getReader(), Maintain.class);
		maintaionDaoImpl.update(maintain);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 刪除
		String id = req.getParameter("id");
		if (id != null && !id.isEmpty()) {
			maintaionDaoImpl.deleteById(Integer.parseInt(id));
		}
	}
}
