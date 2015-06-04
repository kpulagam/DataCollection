package dataCollection;

import java.util.ArrayList;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;

public class dataCollection implements ITestListener, ISuiteListener,
		IInvokedMethodListener{

	private ClassData setdata = null;
	private ArrayList<String> listOfMethods = null;

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		collectCIData(method);
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

	}

	/**
	 * 
	 * Test Suite Related
	 * 
	 */

	@Override
	public void onStart(ISuite suite) {
		
		setdata = new ClassData();
		setdata.setHasTestRunCompleted(false);
		System.err.println("INFO: New Class DataObject Created");

	}

	@Override
	public void onFinish(ISuite suite) {

		setdata.setHasTestRunCompleted(true);

		for (String key : setdata.getClassMethodMap().keySet()) {
			System.out.println("Final Class Name is: " + key);
			System.out.println("The Methods of this class are: "
					+ setdata.getClassMethodMap().get(key));
		}

	}

	private synchronized void collectCIData(IInvokedMethod currentTestRun) {

		String tmpClassName = currentTestRun.getTestMethod().getRealClass()
				.getName();

		if (setdata == null) {
			throw new NullPointerException("The object is null");
		} else {

			if (!setdata.getHasTestRunCompleted()) {

				if (setdata.getClassMethodMap().isEmpty()) {
					listOfMethods = new ArrayList<String>();
					System.err.println("INFO: First method set object created");
					setdata.setClassName(tmpClassName);
					listOfMethods.add(currentTestRun.getTestMethod()
							.getMethodName());					

					setdata.setClassMethodMap(setdata.getClassName(),
							listOfMethods);

				}

				else if (setdata.getClassName() == tmpClassName && !setdata.getClassMethodMap().containsKey(tmpClassName)) {
					listOfMethods.add(currentTestRun.getTestMethod()
							.getMethodName());

					setdata.setClassMethodMap(tmpClassName, listOfMethods);
				}
				
				else if(setdata.getClassName() == tmpClassName && setdata.getClassMethodMap().containsKey(tmpClassName)){
										
					listOfMethods = new ArrayList<String>();
					listOfMethods = setdata.getClassMethodMap().get(tmpClassName);
					listOfMethods.add(currentTestRun.getTestMethod()
							.getMethodName());

					setdata.setClassMethodMap(tmpClassName, listOfMethods);
				}
				
				else if(setdata.getClassName() != tmpClassName && setdata.getClassMethodMap().containsKey(tmpClassName)){
					
					listOfMethods = new ArrayList<String>();
					listOfMethods = setdata.getClassMethodMap().get(tmpClassName);
					listOfMethods.add(currentTestRun.getTestMethod()
							.getMethodName());

					setdata.setClassMethodMap(tmpClassName, listOfMethods);
					
				}

				else if (setdata.getClassName() != tmpClassName && !setdata.getClassMethodMap().containsKey(tmpClassName)) {

					listOfMethods = new ArrayList<String>();
					System.err.println("INFO: New method set object created");
					setdata.setClassName(tmpClassName);
					listOfMethods.add(currentTestRun.getTestMethod()
							.getMethodName());
					setdata.setClassMethodMap(tmpClassName, listOfMethods);

				}

			}

		}

	}

	/**
	 * 
	 * Test Class Related
	 * 
	 */

	@Override
	public void onStart(ITestContext arg0) {



	}

	@Override
	public void onFinish(ITestContext arg0) {
		
	

		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * Test Method Related
	 * 
	 */

	@Override
	public void onTestStart(ITestResult arg0) {
		
		

	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		
		String ClassName = arg0.getTestClass().toString();
		String methodName = arg0.getMethod().getMethodName();
		int result = arg0.getStatus();
		System.err.println("One interested in Pass:"+ClassName + methodName+"------"+result);
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		
		String ClassName = arg0.getTestClass().toString();
		String methodName = arg0.getMethod().getMethodName();
		int result = arg0.getStatus();
		System.err.println("One interested in Fail:"+ClassName + methodName+"------"+result);

	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		
		
		String ClassName = arg0.getTestClass().toString();
		String methodName = arg0.getMethod().getMethodName();
		int result = arg0.getStatus();
		System.err.println("One interested in Skip:"+ClassName + methodName+"------"+result);

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

}
