package peng.servlet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import peng.dao.UserDao;
import peng.dao.UserDaoImpl;
import peng.javabean.User;
import util.Result;


@Path("/user/login")
public class UserService {
	UserDaoImpl userDaoImpl;
	
	private Gson gson = new GsonBuilder()
			.setDateFormat("yyyy/MM/dd HH:mm:ss")
			.create();

	
	public UserService() {
		userDaoImpl = new UserDaoImpl();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(
			@FormDataParam("userNo") String userNo, 
			@FormDataParam("password") String password) {

		// 列印上傳資訊，除錯用
		System.out.println("UserNo: " + userNo);
		System.out.println("Password: " + password);
		
		// 檢查上傳資料是否短少
		if (userNo == null || password == null ) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(new Result(0, "Missing required parameters: uid, password must all be provided."))
					.build();
		}
		try {
			// 建立User物件，帳密一定要有
			User user = new User(userNo, password);
			userDaoImpl.insert(user);
			return Response.ok(new Result(0, "User registered successfully")).build();
		}
		 catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
	
	@GET 
	@Path("/{userNo}/{password}") // 請求的URL為 "/user/userNo/password"
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	public Response validate(@PathParam("userNo") String userNo, @PathParam("password") String password) {
		try {
			User user = userDaoImpl.validate(userNo, password);
			if (user == null) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Result(0, "User not found"))
						.build();
			}
			return Response.ok(user).build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
}
