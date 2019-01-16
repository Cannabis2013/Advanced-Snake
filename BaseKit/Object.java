package BaseKit;


public class Object  {
	
	public Object() {
		id++;
	}
	
	/*
	 * Meta information related
	 */
	
	public String ClassName()
	{
		return getClass().toString();
	}
	
	public void setObjectName(String name)
	{
		objectName = name;
	}
	
	public String ObjectName()
	{
		return objectName;
	}
	
	public static boolean isPureDigit(String str)
	{
		for (int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}
	
	private String objectName = String.format("Object(%d)", id);
	private static int id = 1;
	
	// Trash methods. Just ignore.
}
