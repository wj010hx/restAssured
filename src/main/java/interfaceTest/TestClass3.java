package interfaceTest;
import static io.restassured.RestAssured.*;//最主要的
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;


public class TestClass3 {
	@BeforeClass
	public void initTest() {
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.proxy("proxy.cmcc",8080);
	}
	
	@Test
	public void TestHtmlAssert() {
		//获取html结构体中的字段
		given()
			.queryParam("q","appium")
		.when()
			.get("https://testerhome.com/search").prettyPeek()
		.then()
			.statusCode(200)
			.body("html.head.title", equalTo("appium · 搜索结果 · TesterHome"));
	}
	
	@Test
	public void TestJsonAssert() {
		//获取json结构体中的字段
		given()
		.when()
			.get("https://testerhome.com/api/v3/topics.json").prettyPeek()
		.then()
			.statusCode(200)
			.body("topics.title", hasItem("基于 Appium 的 App UI 遍历工具"))
			.body("topics.title[2]", equalTo("TesterHome 深圳线下沙龙第六期——测试技术前瞻"))
			.body("topics.id[-1]", equalTo(12938))
			.body("topics.findAll { topic -> topic.id == 10254 }.title", hasItem("优质招聘汇总"))//有问题，findAll返回的是数组，需要用hasItem来断言
			.body("topics.find { topic -> topic.id == 10254 }.title", equalTo("优质招聘汇总"))//有问题
			;
	}
	
	@Test
	public void TestJsonAssert2() {
		//获取json结构体中的字段
		given()
		.when()
			.get("https://testerhome.com/api/v3/topics/10254.json").prettyPeek()
		.then()
			.statusCode(200)
			.body("topic.title", equalTo("优质招聘汇总"));
	}
	
	@Test
	public void TestXmlAssert2() {
		//获取xml结构体中的字段，与前面语法一致
	}
	
	@Test
	public void TestExtract() {
		//导出想要的数据，留作后面使用
		String name = given()
		.when()
			.get("https://testerhome.com/api/v3/topics.json").prettyPeek()
		.then()
			.statusCode(200)
			.body("topics.title", hasItem("基于 Appium 的 App UI 遍历工具"))
			.body("topics.title[2]", equalTo("TesterHome 深圳线下沙龙第六期——测试技术前瞻"))
		.extract().path("topics.title[2]")
			;
	
		System.out.println(name);
	}
}
