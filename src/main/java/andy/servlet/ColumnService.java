package andy.servlet;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import andy.dao.column.ColumnDaoImpl;
import andy.javabean.ArticleComment;
import andy.javabean.column.ColumnArticle;
import util.Result;

@Path("/column")
public class ColumnService {
	ColumnDaoImpl columnDaoImpl;
	private Gson gson = new GsonBuilder()
			.setDateFormat("yyyy/MM/dd HH:mm:ss")
			.create();

	// 一定要有沒參數建構式，否則執行會失敗
	public ColumnService() {
		columnDaoImpl = new ColumnDaoImpl();
	}

	@POST
	@Path("/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response add(ArticleComment item) {
		try {
			int result = columnDaoImpl.insert(item);
			if (result > 0) {
				return Response.ok(new Result(result, "Column Comment added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Column Comment added failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@PUT
	@Path("/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 修改
	public Response update(ArticleComment item) {
		try {
			int result = columnDaoImpl.update(item);
			if (result > 0) {
				return Response.ok(new Result(result, "Column Comment updated successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Column Comment updated failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@DELETE
	@Path("/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 刪除留言
	public Response deleteComment(@QueryParam("id") Integer id) {
		try {
			int result = columnDaoImpl.deleteComment(id);
			if (result > 0) {
				return Response.ok(new Result(result, "Column Comment deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Column Comment deleted failed"))
	                .build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@GET // GET請求
	@Path("/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得此專欄的所有意見內容
	public Response findAllComment(@QueryParam("artId") Integer artId) {
		try {
			List<ArticleComment> items = columnDaoImpl.findAllComment(artId);
			System.out.println("ColumnComment.size(): " + items.size());
			/*if (items.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "No columns comment available"))
						.build();
			}*/
			// 成功取得意見：回傳OK狀態與意見資訊
			return Response.ok(gson.toJson(items)).build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@GET // GET請求
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得此使用者參加的所有活動
	public Response findAll(@QueryParam("userId") String userId) {
		try {
			List<ColumnArticle> items = columnDaoImpl.findAll(userId);
			System.out.println("ColumnArticle.size(): " + items.size());
			/*if (items.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "No column article available"))
						.build();
			}*/
			// 成功取得意見：回傳OK狀態與意見資訊
			return Response.ok(gson.toJson(items)).build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
}
