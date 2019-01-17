package BaseKit;

import java.util.ArrayList;
import java.util.List;

public class Object  {
	
	public Object() {
		children = new ArrayList<>();
		id++;
	}
	
	public Object(Object parent)
	{
		id++;
		children = new ArrayList<>();
		P = parent;
		P.addChild(this);
	}
	
	public boolean HasParent()
	{
		if(P != null)
			return true;
		else
			return false;
	}
	
	public void setParent(View parent)
	{
		P = parent;
	}
	
	public Object Parent()
	{
		return P;
	}
	
	public void addChild(Object child)
	{
		if(child != null)
			children.add(child);
		else
			throw new IllegalArgumentException();
	}
	
	public Object ChildAt(int index)
	{
		if(children.size() > index && index >= 0)
			return children.get(index);
		else
			return null;
	}
	
	public Object Child(String childName)
	{
		for (Object child : children) {
			if(child.ObjectName().equals(childName))
				return child;
		}
		return null;
	}
	
	public void RemoveChild(View child)
	{
		children.remove(child);
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
	
	public void print(String outpout)
	{
		System.out.println(outpout);
	}
	
	private String objectName = String.format("Object(%d)", id);
	private static int id = 1;
	protected Object P;
	private List<Object> children;
	
	// Trash methods. Just ignore.
}
