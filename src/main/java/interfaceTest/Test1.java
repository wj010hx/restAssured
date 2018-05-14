package interfaceTest;

import static io.restassured.RestAssured.*;//����Ҫ��
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.testng.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.config.SessionConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;



import static io.restassured.RestAssured.*;

public class Test1 {
	
	   @BeforeClass
	    public static void setupClass(){
		   //ͨ��burpsuit�����������������ᱻץ��
	        RestAssured.proxy("127.0.0.1", 8080);
	    }
	
	@Test
	public void test1() throws Exception{
		Response response = given()
				.get("http://www.jianshu.com/users/recommended?seen_ids=&count=5&only_unfollowed=true");
		// ��ӡ�� response ��body
        response.print();
	}
	
	@Test
	public void test2() throws Exception{
		// ����SSL ����������֧�����е�������
		Response response = given().config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))) 
				.params("q", "�Զ�������", "start", 0, "count", 2) 
				.get("https://api.douban.com/v2/book/search");
		
		// ��ӡ�� response ��body
        response.print();
	}
	
	//test2����һ�ִ���������ʽ
	@Test
	public void test3(){
		// ����SSL ����������֧�����е�������
		Response response = given().config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))) 
				.param("q", "�Զ�������")
				.param("start", 0)
				.param("count", 2)
				.get("https://api.douban.com/v2/book/search");
		
		// ��ӡ�� response ��body
        response.print();
	}
	
	@Test
	public void testSiHan(){
		given().when().get("http://edu.10086.cn").prettyPeek()
		.then()
			.statusCode(200)
			.body("", hasItem("������Ƹ����"));
	}
	
	@Test
    public void testHttpBaseAuth(){
        given()
        	.auth().basic("hogwarts", "123456")
        .when()
                .get("http://101.132.159.87:9002/baidu.html").prettyPeek()
        .then()
                .statusCode(200);
    }
	
	@Test
    public void testJenkinsLogin(){
		

        RestAssured.config=RestAssured.config().sessionConfig(
                new SessionConfig().sessionIdName("JSESSIONID.86912bdc"));

        SessionFilter sessionFilter=new SessionFilter();

        given()
                .filter(sessionFilter)
                .queryParam("Submit", "%E7%99%BB%E5%BD%95")
                .queryParam("j_password", "testerhometesterhome")
                .queryParam("j_username", "testerhome")
                .queryParam("from", "%2F")
        .when()
                .log().all()
                .post("http://47.100.105.183:8081/j_acegi_security_check")
                .prettyPeek()
        .then()
                .log().all()
                .statusCode(302)
        ;

        given()
                .filter(sessionFilter)
        .when().log().all()
                .get("http://47.100.105.183:8081/").prettyPeek()
        .then()
                .statusCode(200);
    }
}
