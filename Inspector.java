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
		
		String className = getClassName(classToInspect);
		String superclassName = getSuperclassName(classToInspect);
		String[] interfaceNames = getInterfaceNames(classToInspect);
		String[] methodNames = getMethodNames(classToInspect);
		String[] constructorNames = getConstructorNames(classToInspect);
		String[] fieldNames = getFieldNames(obj);
		
		System.out.println("Class name: " + className);
		System.out.println("Superclass name: " + superclassName);
		System.out.println("Interfaces of Class:");
		if (interfaceNames.length == 0) {
			System.out.println("\tNone");
		} else {
			for(int i=0; i<interfaceNames.length; i++) {
				System.out.println("\t" + interfaceNames[i]);
			}
		}
		System.out.println("Methods: ");
		for (int i = 0; i < methodNames.length; i++) {
			System.out.println("\t" + methodNames[i]);
		}
		System.out.println("Constructors: ");
		for (int i = 0; i < constructorNames.length; i++) {
			System.out.println("\t" + constructorNames[i]);
		}
		System.out.println("Fields: ");
		for (int i = 0; i < fieldNames.length; i++) {
			System.out.println("\t" + fieldNames[i]);
		}
    }
	
	
	public String getClassName(Class toInspect) {
		
		String className = toInspect.getName();
		return className;
	}
	
	
	public String getSuperclassName(Class toInspect) {
		
		Class superclass = toInspect.getSuperclass();
		String superclassName = superclass.getName();
		return superclassName;
	}
	
	
	public String[] getInterfaceNames(Class toInspect) {
		
		Class[] interfaces = toInspect.getInterfaces();
		String[] interfaceNames = new String[interfaces.length];
		
		if (interfaceNames.length == 0) {
			return interfaceNames;
		} else {
			for(int i=0; i<interfaces.length; i++) {
				interfaceNames[i] = interfaces[i].getName();
			}
			return interfaceNames;
		}
	}
	
	
	public String[] getMethodNames(Class toInspect) {

		Method[] methods = toInspect.getDeclaredMethods();
		String[] methodNames = new String[methods.length];
		
		for (int i=0; i<methods.length; i++) {
			methods[i].setAccessible(true);
			String modifierName = getMethodModiferNames(methods[i]);
			String returnType = getMethodReturnType(methods[i]);
			String methodName = methods[i].getName();
			String[] parameterNames = getMethodParameterNames(methods[i]);
			String formattedParameterNames = formatParameterNames(parameterNames);
			String[] exceptionNames = getMethodExceptionNames(methods[i]);
			String formattedExceptionNames = formatExceptionNames(exceptionNames);
			methodNames[i] = ("method " + (i+1) + ":\t" + modifierName + " " + returnType + " " + methodName + formattedParameterNames + " throws " + formattedExceptionNames);
		}
		
		return methodNames;
	}
	
	
	public String[] getConstructorNames(Class toInspect) {
		
		Constructor[] constructors = toInspect.getDeclaredConstructors();
		String[] constructorNames = new String[constructors.length];
		
		for (int i = 0; i < constructors.length; i++) {
			constructors[i].setAccessible(true);
			String modifierName = getConstructorModifierNames(constructors[i]);
			String constructorName = constructors[i].getName();
			String[] parameterNames = getConstructorParameterNames(constructors[i]);
			String parameterNamesformatted = formatParameterNames(parameterNames);
			constructorNames[i] = ("constructor " + (i+1) + ":\t" + modifierName + " " + constructorName + parameterNamesformatted);			
		}
		
		return constructorNames;
	}
	
	
	public String[] getFieldNames(Object toInspect) {
		
		Field[] fields = toInspect.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String fieldName = fields[i].getName();
			//print fields
			String fieldModifier = Modifier.toString(fields[i].getModifiers());
			String fieldType = fields[i].getType().toString();
			fieldNames[i] = ("field " + (i+1) + ":\t" + fieldModifier + " " + fieldType + " " + fieldName);
			try {
				Object fieldValue = fields[i].get(toInspect);
				fieldNames[i] += (" = " + fieldValue);
			}catch(Exception e) {}
		}
		
		return fieldNames;
	}
	
	
	public String getMethodReturnType(Method toInspect) {
		
		String returnType = toInspect.getReturnType().getName();
		return returnType;
	}
	
	
	public String[] getMethodExceptionNames(Method toInspect) {
		
		Class[] exceptionTypes = toInspect.getExceptionTypes();
		return getParameterNames(exceptionTypes);
	}
	
	
	public String[] getMethodParameterNames(Method toInspect) {
		
		Class[] parameterTypes = toInspect.getParameterTypes();
		return getParameterNames(parameterTypes);
	}
	
	
	public String[] getConstructorParameterNames(Constructor toInspect) {
		
		Class[] parameters = toInspect.getParameterTypes();
		return getParameterNames(parameters);
	}
	
	
	public String getMethodModiferNames(Method toInspect) {
		
		int modsEncoded = toInspect.getModifiers();
		return getModifierNames(modsEncoded);
	}
	
	
	public String getConstructorModifierNames(Constructor toInspect) {
		
		int modsEncoded = toInspect.getModifiers();
		return getModifierNames(modsEncoded);
	}
	

	private String[] getParameterNames(Class[] parameters) {
		
		String[] parameterNames = new String[parameters.length];
		
		for (int i = 0; i < parameters.length; i++) {
			parameterNames[i] = parameters[i].getName();
		}
		
		return parameterNames;
	}
	

	private String getModifierNames(int modsEncoded) {
		
		String modifiers = Modifier.toString(modsEncoded);
		return modifiers;
	}
	
	
	public String formatParameterNames(String[] parameterNames) {
		
		String formattedParameterNames = new String();
		
		if (parameterNames.length == 0) {
			formattedParameterNames = "(No parameters)";
		}
		else {
			formattedParameterNames = "(";
			for(int i=0; i<parameterNames.length; i++) {
				formattedParameterNames += (parameterNames[i] + " param"+(i+1));
				if (i<parameterNames.length-1) {
					formattedParameterNames += ", ";
				}
			}
			formattedParameterNames += ")";
		}
		
		return formattedParameterNames;
	}
	
	
	public String formatExceptionNames(String[] exceptionNames) {
		
		String formattedExceptionNames = new String();
		
		if (exceptionNames.length == 0) {
			formattedExceptionNames= "no exceptions";
		} else {
			for (int i = 0; i < exceptionNames.length; i++) {
				formattedExceptionNames += exceptionNames[i];
				if (i<exceptionNames.length-1) {
					formattedExceptionNames += ", ";
				}
			}
		}
		
		return formattedExceptionNames;
	}
}
