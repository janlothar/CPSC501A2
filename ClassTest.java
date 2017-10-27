//Based on ClassA in Ass2_test_triver

public class ClassTest implements java.io.Serializable, Runnable {
	
	public ClassTest() { val=3; }

	public ClassTest(int i) 
	{

	    try { setVal(i); } catch(Exception e){}
	}

    public void run() { }

    public int getVal(){ return val; }
    public void setVal(int i) throws Exception
	{
	    if ( i < 0 ) 
		throw new Exception("negative value");

	    val = i;
	}

    public String toString() { return "ClassTest"; }

    private void printSomething() { System.out.println("Something"); }

    private int val=3;
    private double val2 = 0.2;
    private boolean val3 = true;

}
