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

import andy.dao.activity.ActivityDaoImpl;
import andy.javabean.activity.Activity;
import andy.javabean.activity.ActivityComment;
import andy.javabean.activity.ActivityJoin;
import util.Result;

@Path("/activity")
public class ActivityService {
	ActivityDaoImpl activityDaoImpl;
	private Gson gson = new GsonBuilder()
			.setDateFormat("yyyy/MM/dd HH:mm:ss")
			.create();

	// 一定要有沒參數建構式，否則執行會失敗
	public ActivityService() {
		activityDaoImpl = new ActivityDaoImpl();
	}

	@POST
	@Path("/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response add(ActivityComment item) {
		try {
			int result = activityDaoImpl.insert(item);
			if (result > 0) {
				return Response.ok(new Result(result, "Activity Comment added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Activity Comment added failed"))
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
	public Response update(ActivityComment item) {
		try {
			int result = activityDaoImpl.update(item);
			if (result > 0) {
				return Response.ok(new Result(result, "Activity Comment updated successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Activity Comment updated failed"))
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
			int result = activityDaoImpl.deleteComment(id);
			if (result > 0) {
				return Response.ok(new Result(result, "Activity Comment deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Activity Comment deleted failed"))
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
	// 取得此活動的所有意見內容
	public Response findAll(@QueryParam("actId") Integer actId) {
		try {
			List<ActivityComment> items = activityDaoImpl.findAllComment(actId);
			System.out.println("ActivityComment.size(): " + items.size());
			if (items.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "No activies comment available"))
						.build();
			}
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

	@POST
	@Path("/join")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response add(ActivityJoin item) {
		try {
			int result = activityDaoImpl.insert(item);
			if (result > 0) {
				return Response.ok(new Result(result, "Activity Join added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Activity Join added failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@DELETE
	@Path("/join")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 刪除留言
	public Response deleteJoin(@QueryParam("id") Integer id, @QueryParam("userId") String userId) {
		try {
			int result = activityDaoImpl.deleteJoin(id, userId);
			if (result > 0) {
				return Response.ok(new Result(result, "Activity Join deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Activity Join deleted failed"))
	                .build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@GET // GET請求
	@Path("/join")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得此使用者參加的所有活動
	public Response findAllJoin(@QueryParam("userId") String userId) {
		try {
			List<Activity> items = activityDaoImpl.findAllJoin(userId);
			System.out.println("Activity.size(): " + items.size());
			if (items.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "No activies available"))
						.build();
			}
			// 成功取得意見：回傳OK狀態與意見資訊
			return Response.ok(gson.toJson(items)).build();
		} catch (Exception e) {
			// 處理執行錯誤
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
			List<Activity> items = activityDaoImpl.findAll(userId);
			System.out.println("Activity.size(): " + items.size());
			if (items.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "No activies available"))
						.build();
			}
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
