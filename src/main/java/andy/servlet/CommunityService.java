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

import andy.dao.community.CommunityDaoImpl;
import andy.javabean.community.CommunityArticle;
import andy.javabean.community.CommunityComment;
import util.Result;

@Path("/community")
public class CommunityService {
	CommunityDaoImpl communityDaoImpl;
	private Gson gson = new GsonBuilder()
			.setDateFormat("yyyy/MM/dd HH:mm:ss")
			.create();

	// 一定要有沒參數建構式，否則執行會失敗
	public CommunityService() {
		communityDaoImpl = new CommunityDaoImpl();
	}

	@POST
	@Path("/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response add(CommunityComment item) {
		try {
			int result = communityDaoImpl.insert(item);
			if (result > 0) {
				return Response.ok(new Result(result, "Community Comment added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Community Comment added failed"))
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
	public Response update(CommunityComment item) {
		try {
			int result = communityDaoImpl.update(item);
			if (result > 0) {
				return Response.ok(new Result(result, "Community Comment updated successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Community Comment updated failed"))
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
			int result = communityDaoImpl.deleteComment(id);
			if (result > 0) {
				return Response.ok(new Result(result, "Community Comment deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Community Comment deleted failed"))
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
	public Response findAllComment(@QueryParam("artId") Integer artId) {
		try {
			List<CommunityComment> items = communityDaoImpl.findAllComment(artId);
			System.out.println("CommunityComment.size(): " + items.size());
			if (items.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "No communities comment available"))
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
			List<CommunityArticle> items = communityDaoImpl.findAll(userId);
			System.out.println("CommunityArticle.size(): " + items.size());
			if (items.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "No communities article available"))
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
