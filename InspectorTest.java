import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

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
	
	@Test
	public void testgetSuperclassName() {
		Inspector testInspector = new Inspector();
		String testSuperclassName = testInspector.getSuperclassName(tester.getClass());
		assertEquals("java.lang.Object", testSuperclassName);
	}
	
	@Test
	public void testgetInterfaceNames() {
		Inspector testInspector = new Inspector();
		String[] testInterfaceName = testInspector.getInterfaceNames(tester.getClass());
		String[] interfaceListToTestAgainst = {"java.io.Serializable", "java.lang.Runnable"};
		//order doesnt matter only content, but assertArrayEquals checks order too. So order arrays beforehand
		Arrays.sort(testInterfaceName);
		Arrays.sort(interfaceListToTestAgainst);
		assertArrayEquals(interfaceListToTestAgainst , testInterfaceName);
	}
	
	@Test
	public void testGetMethodDetails() {
		Inspector testInspector = new Inspector();
		String[] testMethodDetails = testInspector.getMethodDetails(tester.getClass());
		String[] methodListToTestAgainst =
			{
				"public void run(No parameters) throws no exceptions",
				"public java.lang.String toString(No parameters) throws no exceptions",
				"public void setVal(int param1) throws java.lang.Exception",
				"public int getVal(No parameters) throws no exceptions",
				"private void printSomething(No parameters) throws no exceptions"
						
			};
		//order doesnt matter only content, but assertArrayEquals checks order too. So order arrays beforehand
		Arrays.sort(testMethodDetails);
		Arrays.sort(methodListToTestAgainst);
		assertArrayEquals(methodListToTestAgainst, testMethodDetails);
	}
	
	@Test
	public void testGetConstructorDetails() {
		Inspector testInspector = new Inspector();
		String[] testConstructorDetails = testInspector.getConstructorDetails(tester.getClass());
		String[] constructorListToTestAgainst =
			{
				"public ClassTest(No parameters)",
				"public ClassTest(int param1)"
			};
		Arrays.sort(testConstructorDetails);
		Arrays.sort(constructorListToTestAgainst);
		assertArrayEquals(constructorListToTestAgainst, testConstructorDetails);
	}
	
	@Test
	public void testGetFieldDetails() {
		Inspector testInspector = new Inspector();
		String[] testFieldDetails = testInspector.getFieldDetails(tester);
		String[] fieldListToTestAgainst =
			{
					"private int val = 3",
					"private double val2 = 0.2",
					"private boolean val3 = true"
			};
		Arrays.sort(testFieldDetails);
		Arrays.sort(fieldListToTestAgainst);
		assertArrayEquals(fieldListToTestAgainst, testFieldDetails);
	}

}
