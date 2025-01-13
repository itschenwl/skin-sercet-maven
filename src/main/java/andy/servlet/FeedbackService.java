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

import andy.dao.FeedbackDaoImpl;
import andy.javabean.user.Feedback;
import util.Result;

@Path("/user/feedback")
public class FeedbackService {
	FeedbackDaoImpl feedbackDaoImpl;
	private Gson gson = new GsonBuilder()
			.setDateFormat("yyyy/MM/dd HH:mm:ss")
			.create();

	// 一定要有沒參數建構式，否則執行會失敗
	public FeedbackService() {
		feedbackDaoImpl = new FeedbackDaoImpl();
	}

	/*@GET // GET請求
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得所有意見內容
	public Response findAll() {
		try {
			List<Feedback> feedbacks = feedbackDaoImpl.findAll();
			System.out.println("feedbacks.size(): " + feedbacks.size());
			if (feedbacks.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "No feedbacks available"))
						.build();
			}
			// 成功取得意見：回傳OK狀態與意見資訊
			return Response.ok(gson.toJson(feedbacks)).build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}*/

	@GET // GET請求
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得指定意見資訊
	public Response findBy(@QueryParam("id") Integer id) {
		try {
			if (id != null && id > 0) {
				Feedback feedback = feedbackDaoImpl.findById(id);
				if (feedback == null) {
					return Response.status(Response.Status.NOT_FOUND)
							.entity(new Result(0, "Feedback not found"))
							.build();
				}
				return Response.ok(gson.toJson(feedback)).build();
			} else {
				List<Feedback> feedbacks = feedbackDaoImpl.findAll();
				System.out.println("feedbacks.size(): " + feedbacks.size());
				/*if (feedbacks.isEmpty()) {
					return Response.status(Response.Status.NOT_FOUND)
							.entity(new Result(0, "No feedbacks available"))
							.build();
				}*/
				// 成功取得意見：回傳OK狀態與意見資訊
				return Response.ok(gson.toJson(feedbacks)).build();
			}
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response add(Feedback feedback) {
		try {
			int result = feedbackDaoImpl.insert(feedback);
			if (result > 0) {
				return Response.ok(new Result(result, "Feedback added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Feedback added failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 修改
	public Response update(Feedback feedback) {
		try {
			int result = feedbackDaoImpl.update(feedback);
			if (result > 0) {
				return Response.ok(new Result(result, "Feedback updated successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Feedback updated failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 刪除
	public Response delete(@QueryParam("id") Integer id) {
		try {
			int result = feedbackDaoImpl.deleteById(id);
			if (result > 0) {
				return Response.ok(new Result(result, "Feedback deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Feedback deleted failed"))
	                .build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
}
