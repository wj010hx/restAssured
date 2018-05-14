package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import interfaceTest.Test1junit;
import interfaceTest.TestClass5junit;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	Test1junit.class,
	TestClass5junit.class
})

public class DemoSuite {

}
