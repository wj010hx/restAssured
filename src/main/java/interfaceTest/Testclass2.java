package interfaceTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;//最主要的
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Testclass2 {
	@BeforeClass
	public void initTest() {
		RestAssured.useRelaxedHTTPSValidation();
	}
	
	@Test
	public void testGet1() {
		get("http://edu.10086.cn").prettyPeek();
	}
	
	@Test
	public void testGet2() {
		
		//restful两种风格的传参
		//第一种:https://testerhome.com/api/v3/topics.json?limit=2&offset=0&type=last_actived
		Map<String, Object> map = new HashMap<String, Object>();
			map.put("limit", 2);
			map.put("offset", "0");
			map.put("type", "last_actived");
		given().params(map).get("https://testerhome.com/api/v3/topics.json").prettyPeek();
		
		//第二种:https://testerhome.com/{topics}/{topicid}
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("topics", "topics");
		map2.put("topicid", 12192);
		get("https://testerhome.com/{topics}/{topicid}",map2).prettyPeek();
		get("https://testerhome.com/{topics}/","topics",12192).prettyPeek();//参数直接写在后面
	}
	
	@Test
	public void testPost() {
		//第一种直接加参数
		given().param("user[login]", "test@qq.com").param("user[password]", "111111").param("user[remenber_me]", 0)
		.post("https://testerhome.com/account/sign_in").prettyPeek();
		
		//第二种借助map封装打包参数[建议用map]
		Map<String, Object> map = new HashMap<String, Object>();
			map.put("user[login]", "test@qq.com");
			map.put("user[password]", "111111");
			map.put("user[remenber_me]", 0);
		given().params(map).post("https://testerhome.com/account/sign_in").prettyPeek();
		
	}
	
	@Test
	public void testJsonPost() {
		given().body("{\"message\":\"hello world\"}").post("https://testerhome.com/api/v3/topics.json").prettyPeek();
	}
	
	@Test
	public void testFilePost() {
		File file = new File("c:/a.text");
		given().body(file).post("https://testerhome.com/api/v3/topics.json").prettyPeek();
		//上传文件
		given().multiPart(file).post("https://testerhome.com/api/v3/topics.json").prettyPeek();
	}
	
	@Test
	public void testCookie() {
		//有的接口需要登录信息，所以从cookie中取的登录信息附带上一起传给服务器
		given().cookie("username","wangjie").get("https://testerhome.com/account/sign_in").prettyPeek();
		
		given().header("username","wangjie").get("https://testerhome.com/account/sign_in").prettyPeek();
	}
	
	@Test
	public void testUrlEncoding() {
		//王杰=%e7%8e%8b%e6%9d%b0
		given().urlEncodingEnabled(true).param("username", "王杰").param("password", 111111)
		.get("https://testerhome.com/account/sign_in").prettyPeek();
	}
	
	@Test
	public void testJsonPath() {
		Response response = get("https://testerhome.com/api/v3/topics.json?limit=2&offset=0&type=last_actived");
		List<Object> list = new ArrayList<Object>();
		list = response.jsonPath().getList("topics");
		System.out.println(list.size());//可以取得响应体的个数，轻松的做断言
		System.out.println( response.jsonPath().getInt("topics[0].id"));//第一个topic的id值
	}

}
