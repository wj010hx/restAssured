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
		   //ȫ���ߴ���
		   //С����:ͨ��burpsuit����������������������ߴ����ᱻץ��
	        RestAssured.proxy("127.0.0.1", 8080);
	    }
	   
	   @Test
		public void TestProxy() {
			//���������ߴ���
			given().proxy("proxy.cmcc",8080)
			.when()
				.get("https://testerhome.com/api/v3/topics/10254.json").prettyPeek()
			.then()
				.statusCode(200)
				.body("topic.title", equalTo("������Ƹ����"));
		}
	   
	   @Test
	   public void testJsonPost() {
		   Map<String, Object> data = new HashMap<String, Object>();
		   data.put("id", 6040);
		   data.put("title", "ͨ������װappium");
		   data.put("name", "����");
		   //����HashMap�Զ�ת��Ϊjson�ĸ�ʽ�������ݸ��ӿڣ�Ҳ����ѡ��xml��html��
		   given()
		   		.contentType(ContentType.JSON)
		   		.body(data)
		   .when()
		   		.post("http://www.baidu.com")
		   .then()
		   		.statusCode(200)
		   		.time(lessThan(1500L))//��Ӧʱ��С��1500����
		   		;
	   }
	   
	   @Test
	   public void testMultiAPI() {
		   
		   //extractһ���ֶΡ�����ֶ�(��������respons���ٷֱ�ȡ���ֵ) ,�Ա����Ľӿ�ʹ��
	   }
	   
	   @Test
	   public void testSpecReUse() {
		   //��ͨ�õļ������Դ�����Ժ���
		   ResponseSpecification rs = new ResponseSpecBuilder().build();
		   rs.statusCode(200);
		   rs.time(lessThan(1500L));
		   rs.body(not(contains("error")));
		   
		   //ʹ��
		   given().get("http://www.baidu.com")
		   .then().spec(rs);
		   
	   }
	   
	   @Test
	   public void testFliter() {
		   //���ܺ�ǿ��,ûϸ����ɱ���
		   //schemaҲû��1��20���Ӵ�
		   RestAssured.filters();
	   }
}
