import java.util.Vector;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		
		Class classToInspect = obj.getClass();
		
		getClassName(classToInspect);
		
		if(recursive) {
			
		}
	       
    }
	
	public void getClassName(Class toInspect) {
		
		String className = toInspect.getName();
		System.out.println("Class name: " + className);
	}
}
