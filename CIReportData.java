package com.paypal.test.gops.admin.listeener;

class CIReportData {
	
	private String className;
	private String suiteName;
	private String testTageName;
	private boolean result;
	private String[] classMethods;
	private boolean isClassExecutionCompleted;
	
	protected void setClassName(String className){
		this.className = className;
	}
	
	protected String className(){
		return this.className;
	}
	
	protected void setSuiteName(String suiteName){
		this.suiteName = suiteName;
	}
	
	protected String getSuiteName(){
		return this.suiteName;
		
	}
	
	protected void setTestTagName(String testTagName){
		this.testTageName = testTagName;
	}
	
	protected String getTestTagName(){
		return this.testTageName;
	}
	
	protected void setResult(boolean result){
		this.result = result;
	}
	
	protected boolean getResult(){
		return this.result;
	}
	
	protected void setClassMethods(String[] classMethods){
		this.classMethods = classMethods;
	}
	
	protected String[] getClassMethods(){
		return classMethods;
	}
	
	protected void setIsClassExecutionCompleted(boolean isClassExecutionCompleted){
		this.isClassExecutionCompleted = isClassExecutionCompleted;
	}
	
	protected boolean getIsClassExecutionCompleted(){
		return isClassExecutionCompleted;
	}
	
}

