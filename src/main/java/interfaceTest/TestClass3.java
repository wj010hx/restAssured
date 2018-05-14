package interfaceTest;
import static io.restassured.RestAssured.*;//����Ҫ��
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
		//��ȡhtml�ṹ���е��ֶ�
		given()
			.queryParam("q","appium")
		.when()
			.get("https://testerhome.com/search").prettyPeek()
		.then()
			.statusCode(200)
			.body("html.head.title", equalTo("appium �� ������� �� TesterHome"));
	}
	
	@Test
	public void TestJsonAssert() {
		//��ȡjson�ṹ���е��ֶ�
		given()
		.when()
			.get("https://testerhome.com/api/v3/topics.json").prettyPeek()
		.then()
			.statusCode(200)
			.body("topics.title", hasItem("���� Appium �� App UI ��������"))
			.body("topics.title[2]", equalTo("TesterHome ��������ɳ�������ڡ������Լ���ǰհ"))
			.body("topics.id[-1]", equalTo(12938))
			.body("topics.findAll { topic -> topic.id == 10254 }.title", hasItem("������Ƹ����"))//�����⣬findAll���ص������飬��Ҫ��hasItem������
			.body("topics.find { topic -> topic.id == 10254 }.title", equalTo("������Ƹ����"))//������
			;
	}
	
	@Test
	public void TestJsonAssert2() {
		//��ȡjson�ṹ���е��ֶ�
		given()
		.when()
			.get("https://testerhome.com/api/v3/topics/10254.json").prettyPeek()
		.then()
			.statusCode(200)
			.body("topic.title", equalTo("������Ƹ����"));
	}
	
	@Test
	public void TestXmlAssert2() {
		//��ȡxml�ṹ���е��ֶΣ���ǰ���﷨һ��
	}
	
	@Test
	public void TestExtract() {
		//������Ҫ�����ݣ���������ʹ��
		String name = given()
		.when()
			.get("https://testerhome.com/api/v3/topics.json").prettyPeek()
		.then()
			.statusCode(200)
			.body("topics.title", hasItem("���� Appium �� App UI ��������"))
			.body("topics.title[2]", equalTo("TesterHome ��������ɳ�������ڡ������Լ���ǰհ"))
		.extract().path("topics.title[2]")
			;
	
		System.out.println(name);
	}
}
