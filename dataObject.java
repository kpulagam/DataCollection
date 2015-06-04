package dataObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class dataObject {

	private boolean hasTestRunCompleted ;
	private LinkedHashMap<String,ArrayList<String>> classMethodMap = new LinkedHashMap<String, ArrayList<String>>();
	
	private String className;
	
	
	public String getClassName() {
		return this.className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public boolean getHasTestRunCompleted() {
		
		return this.hasTestRunCompleted;
	}
	public void setHasTestRunCompleted(boolean isClassExecutinCompleted) {
		this.hasTestRunCompleted = isClassExecutinCompleted;
	}
	public Map<String, ArrayList<String>> getClassMethodMap() {
		
		return this.classMethodMap;
	}
	public void setClassMethodMap(String className, ArrayList<String> classMethodSet) {
		this.classMethodMap.put(className, classMethodSet ) ;
		
	}
	
	
}
