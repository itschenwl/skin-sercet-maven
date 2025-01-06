package aaron.servlet;

import java.io.IOException;
import java.util.List;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import aaron.bean.Evaluate;
import aaron.dao.EvaluateDaoImpl;

@WebServlet("/product/evaluate")
public class EvaluateServlet extends HttpServlet {

	// 序列化控制識別碼
	private static final long serialVersionUID = 1L;

	private EvaluateDaoImpl evaluateDaoimpl = new EvaluateDaoImpl();

	// 查詢
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 取產編
		Gson gson = new Gson();
		String prodNo = req.getParameter("prodid");

		// 查詢評論
		List<Evaluate> comments = evaluateDaoimpl.getCommentsByProduct(prodNo);

		// 回應
		resp.setContentType("application/json; charset=UTF-8");

		if (comments == null || comments.isEmpty()) {
			resp.getWriter().write("尚無評論");
		} else {
			resp.getWriter().write(gson.toJson(comments));
		}
	}

	// 新增
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");

		Evaluate newEvaluate = buildEvaluateFromRequest(req);

		boolean add = evaluateDaoimpl.addComment(newEvaluate);

		if (add) {
			resp.getWriter().write("新增成功");
		} else {
			resp.getWriter().write("新增失敗");
		}
	}

	// 修改
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain; charset=UTF-8");

		Evaluate updatedEvaluate = buildEvaluateFromRequest(req);
		
		System.out.println("doPost");

		// 從 userNo 中比對該筆 evalNo 是否存在
		String userNo = updatedEvaluate.getUserNo();
		int evalNo = updatedEvaluate.getEvalNo();

		boolean isOwner = evaluateDaoimpl.isOwner(userNo, evalNo);

		// 比對該筆留言跟用戶是否匹配
		boolean updated = isOwner && evaluateDaoimpl.updateComment(updatedEvaluate);

		if (updated) {
			resp.getWriter().write("比對成功");
		} else {
			resp.getWriter().write("比對失敗");
		}
	}

	// 刪除
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain; charset=UTF-8");

		
//		String userNo = updatedEvaluate.getUserNo();
//		int evalNo = updatedEvaluate.getEvalNo();
		
		String userNo = req.getParameter("userNo");
		int evalNo = Integer.parseInt(req.getParameter("evalNo"));

		// 從 userNo 中比對該筆 evalNo 是否存在

		boolean isOwner = evaluateDaoimpl.isOwner(userNo, evalNo);

		
		if (isOwner) {
			boolean deleted = evaluateDaoimpl.deleteComment(evalNo);
			
			if (deleted) {
				resp.getWriter().write("刪除成功");
			} else {
				resp.getWriter().write("刪除失敗");
			}
		}

	}

	// 處理 JSON 資料
	private Evaluate buildEvaluateFromRequest(HttpServletRequest req) throws IOException {

		Gson gson = new Gson();
		return gson.fromJson(new InputStreamReader(req.getInputStream(), "UTF-8"), Evaluate.class);
	}

}
