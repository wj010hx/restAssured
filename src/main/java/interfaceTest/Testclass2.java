package interfaceTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;//����Ҫ��
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
		
		//restful���ַ��Ĵ���
		//��һ��:https://testerhome.com/api/v3/topics.json?limit=2&offset=0&type=last_actived
		Map<String, Object> map = new HashMap<String, Object>();
			map.put("limit", 2);
			map.put("offset", "0");
			map.put("type", "last_actived");
		given().params(map).get("https://testerhome.com/api/v3/topics.json").prettyPeek();
		
		//�ڶ���:https://testerhome.com/{topics}/{topicid}
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("topics", "topics");
		map2.put("topicid", 12192);
		get("https://testerhome.com/{topics}/{topicid}",map2).prettyPeek();
		get("https://testerhome.com/{topics}/","topics",12192).prettyPeek();//����ֱ��д�ں���
	}
	
	@Test
	public void testPost() {
		//��һ��ֱ�ӼӲ���
		given().param("user[login]", "test@qq.com").param("user[password]", "111111").param("user[remenber_me]", 0)
		.post("https://testerhome.com/account/sign_in").prettyPeek();
		
		//�ڶ��ֽ���map��װ�������[������map]
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
		//�ϴ��ļ�
		given().multiPart(file).post("https://testerhome.com/api/v3/topics.json").prettyPeek();
	}
	
	@Test
	public void testCookie() {
		//�еĽӿ���Ҫ��¼��Ϣ�����Դ�cookie��ȡ�ĵ�¼��Ϣ������һ�𴫸�������
		given().cookie("username","wangjie").get("https://testerhome.com/account/sign_in").prettyPeek();
		
		given().header("username","wangjie").get("https://testerhome.com/account/sign_in").prettyPeek();
	}
	
	@Test
	public void testUrlEncoding() {
		//����=%e7%8e%8b%e6%9d%b0
		given().urlEncodingEnabled(true).param("username", "����").param("password", 111111)
		.get("https://testerhome.com/account/sign_in").prettyPeek();
	}
	
	@Test
	public void testJsonPath() {
		Response response = get("https://testerhome.com/api/v3/topics.json?limit=2&offset=0&type=last_actived");
		List<Object> list = new ArrayList<Object>();
		list = response.jsonPath().getList("topics");
		System.out.println(list.size());//����ȡ����Ӧ��ĸ��������ɵ�������
		System.out.println( response.jsonPath().getInt("topics[0].id"));//��һ��topic��idֵ
	}

}
