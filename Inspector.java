import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;
import java.util.zip.InflaterInputStream;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		
		Class classToInspect = obj.getClass();
		
		getClassName(classToInspect);
		getSuperclassName(classToInspect);
		getInterfaceNames(classToInspect);
		getMethodNames(classToInspect);
		
		if(recursive) {
			
		}
	       
    }
	
	public void getClassName(Class toInspect) {
		
		String className = toInspect.getName();
		System.out.println("Class name: " + className);
	}
	
	public void getSuperclassName(Class toInspect) {
		
		Class superclass = toInspect.getSuperclass();
		String superclassName = superclass.getName();
		System.out.println("Super class name: " + superclassName);
	}
	
	public void getInterfaceNames(Class toInspect) {
		
		Class[] interfaces = toInspect.getInterfaces();
		String[] interfaceNames = new String[interfaces.length];
		System.out.println("Interfaces of Class:");
		
		for(int i=0; i<interfaces.length; i++) {
			interfaceNames[i] = interfaces[i].getName();
			System.out.println("\t" + interfaceNames[i]);
		}
	}
	
	public void getMethodNames(Class toInspect) {
//		System.out.println("DEBUG: class=" + toInspect.getName());
		Method[] toInspectMethods = toInspect.getDeclaredMethods();
//		System.out.println("DEBUG: :length of toInspectMethods= " + toInspectMethods.length);
		String[] methodNames = new String[toInspectMethods.length];
		
		for (int i=0; i<toInspectMethods.length; i++) {
			methodNames[i] = toInspectMethods[i].getName();
			//Print method names
			System.out.println("Method: " + methodNames[i]);
			//print exceptions thrown
			System.out.println("\tExceptions thrown: ");
			String[] exceptionNames = getMethodExceptionNames(toInspectMethods[i]);
			if (exceptionNames.length == 0) {
				System.out.println("\t\tNone");
			}
			else {
				for(String exceptionName : exceptionNames) {
					System.out.println("\t\t" + exceptionName);
				}
			}
			//print parameter types
			System.out.println("\tParameter types: ");
			String[] parameterNames = getParameterNames(toInspectMethods[i]);
			if (parameterNames.length == 0) {
				System.out.println("\t\tNone");
			}
			else {
				for(String parameterName : parameterNames) {
					System.out.println("\t\t" + parameterName);
				}
			}
			//print return type
			System.out.println("\tReturn type: ");
			System.out.println("\t\t" + getReturnType(toInspectMethods[i]));
			//print modifiers
			System.out.println("\tModifiers: ");
			System.out.println("\t\t" + Modifier.toString(toInspectMethods[i].getModifiers()));
		}
	}
	
	public String[] getMethodExceptionNames(Method toInspect) {
		
		Class[] exceptionTypes = toInspect.getExceptionTypes();
		String[] exceptionNames = new String[exceptionTypes.length];
		
		for(int i=0; i<exceptionTypes.length; i++) {
			exceptionNames[i] = exceptionTypes[i].getName();
		}
		
		return exceptionNames;
	}
	
	public String[] getParameterNames(Method toInspect) {
		
		Class[] parameterTypes = toInspect.getParameterTypes();
		String[] parameterNames = new String[parameterTypes.length];
		
		for(int i=0; i<parameterTypes.length; i++) {
			parameterNames[i] = parameterTypes[i].getName();
		}
		
		return parameterNames;
	}
	
	public String getReturnType(Method toInspect) {
		
		String returnType = toInspect.getReturnType().getName();
		return returnType;
	}
}
