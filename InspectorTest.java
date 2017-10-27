import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class InspectorTest {
	
	public ClassTest tester = new ClassTest();
	
	@Before
	public void testClassSetup() {
		tester = new ClassTest();
	}
	
	@After
	public void testClassWipe() {
		tester = null;
	}

	@Test
	public void testGetClassName() {
		Inspector testInspector = new Inspector();
		String testClassName = testInspector.getClassName(tester.getClass());
		assertEquals("ClassTest", testClassName);
	}

}
