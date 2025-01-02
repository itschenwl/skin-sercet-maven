package util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
// @ApplicationPath 標註REST資源的起始網址
@ApplicationPath("rest")
public class RootApplication extends ResourceConfig {
    public RootApplication() {
    	// packages 指定專案 package
        packages("andy.servlet");
        // 註冊要使用multipart功能
        register(org.glassfish.jersey.media.multipart.MultiPartFeature.class);
    }
}
