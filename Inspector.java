import java.util.Vector;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		
		Class classToInspect = obj.getClass();
		
		getClassName(classToInspect);
		getSuperclassName(classToInspect);
		getInterfaceNames(classToInspect);
		
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
}
