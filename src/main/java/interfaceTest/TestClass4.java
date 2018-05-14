package interfaceTest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import groovyjarjarantlr.LexerSharedInputState;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class TestClass4 {
	
	   @BeforeClass
	    public static void setupClass(){
		   //全局走代理
		   //小技巧:通过burpsuit开代理服务器，设置请求走代理，会被抓包
	        RestAssured.proxy("127.0.0.1", 8080);
	    }
	   
	   @Test
		public void TestProxy() {
			//单独用例走代理
			given().proxy("proxy.cmcc",8080)
			.when()
				.get("https://testerhome.com/api/v3/topics/10254.json").prettyPeek()
			.then()
				.statusCode(200)
				.body("topic.title", equalTo("优质招聘汇总"));
		}
	   
	   @Test
	   public void testJsonPost() {
		   Map<String, Object> data = new HashMap<String, Object>();
		   data.put("id", 6040);
		   data.put("title", "通过代理安装appium");
		   data.put("name", "王杰");
		   //将把HashMap自动转化为json的格式发送数据给接口，也可以选择xml、html等
		   given()
		   		.contentType(ContentType.JSON)
		   		.body(data)
		   .when()
		   		.post("http://www.baidu.com")
		   .then()
		   		.statusCode(200)
		   		.time(lessThan(1500L))//响应时间小于1500毫秒
		   		;
	   }
	   
	   @Test
	   public void testMultiAPI() {
		   
		   //extract一个字段、多个字段(导出整个respons后再分别取多个值) ,以便后面的接口使用
	   }
	   
	   @Test
	   public void testSpecReUse() {
		   //把通用的几个断言打包，以后复用
		   ResponseSpecification rs = new ResponseSpecBuilder().build();
		   rs.statusCode(200);
		   rs.time(lessThan(1500L));
		   rs.body(not(contains("error")));
		   
		   //使用
		   given().get("http://www.baidu.com")
		   .then().spec(rs);
		   
	   }
	   
	   @Test
	   public void testFliter() {
		   //功能很强大,没细讲，杀手锏
		   //schema也没讲1：20分钟处
		   RestAssured.filters();
	   }
}
