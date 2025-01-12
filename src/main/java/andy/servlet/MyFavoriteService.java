package andy.servlet;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import andy.dao.myfavorite.MyFavoriteDaoImpl;
import andy.javabean.Favorite;
import andy.javabean.activity.Activity;
import andy.javabean.column.ColumnArticle;
import andy.javabean.community.CommunityArticle;
import andy.javabean.product.Product;
import util.Result;

@Path("/user/favorite")
public class MyFavoriteService {

	MyFavoriteDaoImpl favoriteDaoImpl;
	private Gson gson = new GsonBuilder()
			.setDateFormat("yyyy/MM/dd HH:mm:ss")
			.create();

	// 一定要有沒參數建構式，否則執行會失敗
	public MyFavoriteService() {
		favoriteDaoImpl = new MyFavoriteDaoImpl();
	}

	// 活動
	@GET // GET請求
	@Path("/activity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得指定意見資訊
	public Response findActivity(@QueryParam("userId") String userId) {
		try {
			if (userId != null && !userId.isEmpty()) {
				List<Activity> activities = favoriteDaoImpl.findActivities(userId);
				System.out.println("activities.size(): " + activities.size());
				/*if (activities.isEmpty()) {
					return Response.status(Response.Status.NOT_FOUND)
							.entity(new Result(0, "No favorite activities available"))
							.build();
				}*/
				// 成功取得意見：回傳OK狀態與意見資訊
				return Response.ok(gson.toJson(activities)).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "id or userId is null"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@POST
	@Path("/activity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response addActivity(Favorite<Integer> favorite) {
		try {
			int result = favoriteDaoImpl.insertActivity(favorite.getId(), favorite.getUserId());
			if (result > 0) {
				return Response.ok(new Result(result, "Favorite Activity added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Favorite Activity added failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@DELETE
	@Path("/activity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 刪除
	public Response deleteActivity(@QueryParam("id") Integer id, @QueryParam("userId") String userId) {
		try {
			int result = favoriteDaoImpl.deleteActivity(id, userId);
			if (result > 0) {
				return Response.ok(new Result(result, "Favorite Activity deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Favorite Activity deleted failed"))
	                .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	// 社群
	@GET // GET請求
	@Path("/community")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得指定意見資訊
	public Response findCommunity(@QueryParam("userId") String userId) {
		try {
			if (userId != null && !userId.isEmpty()) {
				List<CommunityArticle> communities = favoriteDaoImpl.findCommunities(userId);
				System.out.println("communities.size(): " + communities.size());
				/*if (communities.isEmpty()) {
					return Response.status(Response.Status.NOT_FOUND)
							.entity(new Result(0, "No favorite communities available"))
							.build();
				}*/
				// 成功取得意見：回傳OK狀態與意見資訊
				return Response.ok(gson.toJson(communities)).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "userId is null"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@POST
	@Path("/community")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response likeCommunity(Favorite<Integer> favorite) {
		try {
			int result = favoriteDaoImpl.insertCommunity(favorite.getId(), favorite.getUserId());
			if (result > 0) {
				return Response.ok(new Result(result, "Favorite Community added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Favorite Community added failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@DELETE
	@Path("/community")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 刪除
	public Response unLikeCommunity(@QueryParam("id") Integer id, @QueryParam("userId") String userId) {
		try {
			int result = favoriteDaoImpl.deleteCommunity(id, userId);
			if (result > 0) {
				return Response.ok(new Result(result, "Favorite Community deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Favorite Community deleted failed"))
	                .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
	
	// 專欄
	@GET // GET請求
	@Path("/column")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得指定意見資訊
	public Response findColumn(@QueryParam("userId") String userId) {
		try {
			if (userId != null && !userId.isEmpty()) {
				List<ColumnArticle> columns = favoriteDaoImpl.findColumns(userId);
				System.out.println("communities.size(): " + columns.size());
				/*if (columns.isEmpty()) {
					return Response.status(Response.Status.NOT_FOUND)
							.entity(new Result(0, "No favorite columns available"))
							.build();
				}*/
				// 成功取得意見：回傳OK狀態與意見資訊
				return Response.ok(gson.toJson(columns)).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "id or userId is null"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@POST
	@Path("/column")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response addColumn(Favorite<Integer> favorite) {
		try {
			int result = favoriteDaoImpl.insertColumn(favorite.getId(), favorite.getUserId());
			if (result > 0) {
				return Response.ok(new Result(result, "Favorite Column added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Favorite Column added failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@DELETE
	@Path("/column")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 刪除
	public Response deleteColumn(@QueryParam("id") Integer id, @QueryParam("userId") String userId) {
		try {
			int result = favoriteDaoImpl.deleteColumn(id, userId);
			if (result > 0) {
				return Response.ok(new Result(result, "Favorite Column deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Favorite Column deleted failed"))
	                .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
	
	// 產品
	@GET // GET請求
	@Path("/product")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	// 取得指定意見資訊
	public Response findProduct(@QueryParam("userId") String userId) {
		try {
			if (userId != null && !userId.isEmpty()) {
				List<Product> products = favoriteDaoImpl.findProducts(userId);
				System.out.println("communities.size(): " + products.size());
				/*if (products.isEmpty()) {
					return Response.status(Response.Status.NOT_FOUND)
							.entity(new Result(0, "No favorite products available"))
							.build();
				}*/
				// 成功取得意見：回傳OK狀態與意見資訊
				return Response.ok(gson.toJson(products)).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "id or userId is null"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@POST
	@Path("/product")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 新增
	public Response addProduct(Favorite<String> favorite) {
		try {
			int result = favoriteDaoImpl.insertProduct(favorite.getId(), favorite.getUserId());
			if (result > 0) {
				return Response.ok(new Result(result, "Favorite Product added successfully")).build();
			}
			return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Result(0, "Favorite Product added failed"))
                    .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}

	@DELETE
	@Path("/product")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 刪除
	public Response deleteProduct(@QueryParam("id") String id, @QueryParam("userId") String userId) {
		try {
			int result = favoriteDaoImpl.deleteProduct(id, userId);
			if (result > 0) {
				return Response.ok(new Result(result, "Favorite Product deleted successfully")).build();
			}
	        return Response.status(Response.Status.NOT_FOUND)
	        		.entity(new Result(0, "Favorite Product deleted failed"))
	                .build();
		} catch (Exception e) {
			// 處理執行錯誤
			System.out.println("error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
}
