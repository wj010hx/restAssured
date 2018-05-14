package interfaceTest;

import static io.restassured.RestAssured.*;//����Ҫ��
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.*;


import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.config.SessionConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;



import static io.restassured.RestAssured.*;

public class Test1junit {
	
	   @BeforeClass
	    public static void setupClass(){
		   //ͨ��burpsuit�����������������ᱻץ��
	       // RestAssured.proxy("127.0.0.1", 8080);
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
	

}
