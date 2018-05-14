package interfaceTest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.*;

public class TestClass5junit {
	
	@BeforeClass
	public static void  testBeforeClass() {
		System.out.println("我是BeforeClass");
	}
	
	@Before
	public void testBefore() {
		System.out.println("我是Before");
	}
	
	@Test
	public void test1() {
		System.out.println("我是test1");
	}
	
	@Test
	public void test2() {
		System.out.println("我是test2");
	}
	
	@Test
	public void test3() {
		System.out.println("我是test3");
	}
	
	@After
	public  void testAfter() {
		System.out.println("我是After");
	}
	
	@AfterClass
	public static void testAfterClass() {
		System.out.println("我是AfterClass");
	}
	
	

}
