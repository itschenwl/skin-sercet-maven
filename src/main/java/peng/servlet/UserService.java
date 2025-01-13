package peng.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 修改user
	public Response update(User user) {
		try {
			userDaoImpl.update(user);
			return Response.ok(new Result(0, "User updated successfully")).build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
	
	@PUT
	@Path("/userIntro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// 修改user
	public Response updateIntro(User user) {
		try {
			userDaoImpl.IntroUpdate(user);
			return Response.ok(new Result(0, "User updated successfully")).build();
		} catch (Exception e) {
			// 處理執行錯誤
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Result(0, e.getMessage()))
                    .build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@QueryParam("userNo") String userNo, @QueryParam("password") String password) {

		// 列印上傳資訊，除錯用
		System.out.println("UserNo: " + userNo);
		System.out.println("Password: " + password);
		
		// 檢查上傳資料是否短少
		if (userNo == null || password == null ) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(new Result(0, "Missing required parameters: userNo, password must all be provided."))
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
	@Produces(MediaType.APPLICATION_JSON) // 輸出資料轉成JSON
	public Response validate(@QueryParam("userNo") String userNo, @QueryParam("password") String password) {
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
