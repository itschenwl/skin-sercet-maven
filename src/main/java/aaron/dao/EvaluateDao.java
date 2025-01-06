package aaron.dao;

import java.util.List;
import aaron.bean.Evaluate;


public interface EvaluateDao {
	List<Evaluate> getCommentsByProduct(String prodNo); // 查詢留言
	boolean addComment(Evaluate comment); // 新增留言
	boolean deleteComment(int evalNo); // 刪除留言
	boolean updateComment(Evaluate comment); // 修改留言
	boolean addReport(Evaluate report); // 新增檢舉項目
}
