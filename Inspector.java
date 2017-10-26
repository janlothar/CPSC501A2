import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;
import java.util.zip.InflaterInputStream;

public class Inspector {

	Boolean recurse = false;
	
	public void inspect(Object obj, boolean recursive) {
		
		if(recursive) {
			recurse = true;
		}
		else {
			recurse = false;
		}
		
		Class classToInspect = obj.getClass();
		
		getClassName(classToInspect);
		getSuperclassName(classToInspect);
		getInterfaceNames(classToInspect);
		getMethodNames(classToInspect);
		getConstructorNames(classToInspect);
		getFieldNames(obj);
		
		
	       
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

		Method[] toInspectMethods = toInspect.getDeclaredMethods();
		String[] methodNames = new String[toInspectMethods.length];
		
		for (int i=0; i<toInspectMethods.length; i++) {
			toInspectMethods[i].setAccessible(true);
			methodNames[i] = toInspectMethods[i].getName();
			//Print method names
			System.out.println("\nMethod: " + methodNames[i]);
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
			String[] parameterNames = getMethodParameterNames(toInspectMethods[i]);
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
			System.out.println("\t\t" + getMethodReturnType(toInspectMethods[i]));
			//print modifiers
			System.out.println("\tModifiers: ");
			System.out.println("\t\t" + getMethodModiferNames(toInspectMethods[i]));
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
	
	public String[] getMethodParameterNames(Method toInspect) {
		
		Class[] parameterTypes = toInspect.getParameterTypes();
		String[] parameterNames = new String[parameterTypes.length];
		
		for(int i=0; i<parameterTypes.length; i++) {
			parameterNames[i] = parameterTypes[i].getName();
		}
		
		return parameterNames;
	}
	
	public String getMethodReturnType(Method toInspect) {
		
		String returnType = toInspect.getReturnType().getName();
		return returnType;
	}
	
	public String getMethodModiferNames(Method toInspect) {
		
		int modsEncoded = toInspect.getModifiers();
		String modifiers = Modifier.toString(modsEncoded);
		return modifiers;
	}
	
	public void getConstructorNames(Class toInspect) {
		
		Constructor[] constructors = toInspect.getDeclaredConstructors();
		String[] constructorNames = new String[constructors.length];
		
		for (int i = 0; i < constructors.length; i++) {
			constructors[i].setAccessible(true);
			constructorNames[i] = constructors[i].getName();
			//print constructor names
			System.out.println("\nConstructor: " + constructorNames[i]);
			//print constructor parameters
			System.out.println("\tParameters:");
			String[] parameterNames = getConstructorParameterNames(constructors[i]);
			if (parameterNames.length == 0) {
				System.out.println("\t\tNone");
			}
			else {
				for(String parameterName : parameterNames) {
					System.out.println("\t\t" + parameterName);
				}
			}
			//print constructor modifiers
			System.out.println("\tModifiers: ");
			System.out.println("\t\t" + getConstructorModifierNames(constructors[i]));
		}
		
	}
	
	public String[] getConstructorParameterNames(Constructor toInspect) {
		
		Class[] parameters = toInspect.getParameterTypes();
		String[] parameterNames = new String[parameters.length];
		
		for (int i = 0; i < parameters.length; i++) {
			parameterNames[i] = parameters[i].getName();
		}
		
		return parameterNames;
	}
	
	public String getConstructorModifierNames(Constructor toInspect) {
		
		int modsEncoded = toInspect.getModifiers();
		String modifiers = Modifier.toString(modsEncoded);
		return modifiers;
	}
	
	public void getFieldNames(Object toInspect) {
		
		Field[] fields = toInspect.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		System.out.println("Fields: ");
		
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			fieldNames[i] = fields[i].getName();
			//print fields
			String fieldModifier = Modifier.toString(fields[i].getModifiers());
			String fieldType = fields[i].getType().toString();
			System.out.print("\t" + " " + fieldType + " " + fieldModifier + " " + fieldNames[i]);
			try {
				Object fieldValue = fields[i].get(toInspect);
				System.out.print(" = " + fieldValue + "\n");
			}catch(Exception e) {}
		}
	}
}
