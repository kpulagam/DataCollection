package com.paypal.test.gops.admin.listeener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.bson.Document;
import org.json.JSONException;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import com.paypal.test.gops.admin.listeener.MongoDAO;

public class ReportListener implements ITestListener, ISuiteListener,
		IInvokedMethodListener {

	private ClassData setdata = null;
	private HashMap<String, Integer> listOfAllMethods = null;
	private List<ITestNGMethod> initialMethods = null;
	private String testClassStatus = null;
	private CIHelper CIHelper = new CIHelper();
	private Document doc = new Document();
	private MongoDAO databaseObject;

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

		try {
			CIHelper.collectCIData(method, testResult, setdata, testClassStatus);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String className : setdata.getTestClassResultSet().keySet()) {

			doc = new Document("ClassName", className).append("Status",
					setdata.getTestClassResultSet().get(className)).append(
					"Methods", CIHelper.setMethodStatus(setdata, className));
			databaseObject.writeIntoDb(doc,className);
			

		}
		System.out.println("**********");

	}

	/**
	 * 
	 * Test Suite Related
	 * 
	 */

	@Override
	public void onStart(ISuite suite) {
		try {

			Date date = new Date();
			setdata = new ClassData();
			// finalSetdata = new ClassData();

			initialMethods = new ArrayList<ITestNGMethod>();
			setdata.setHasTestRunCompleted(false);
			setdata.setSuiteName(suite.getName());
			setdata.setTestRunStartTime(date.getTime());
			databaseObject = new MongoDAO(setdata.getSuiteName());
			CIHelper.createClassMethodMap(listOfAllMethods, initialMethods,
					suite, setdata);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ISuite suite) {
		Date date = new Date();
		setdata.setHasTestRunCompleted(true);
		setdata.setTestRunEndTime(date.getTime());
		System.err.println("Info: Suite name is :" + setdata.getSuiteName());
		System.err.println("Info: Test run start time is :"
				+ setdata.getTestRunStartTime());
		System.err.println("Info: Test run end time is :"
				+ setdata.getTestRunEndTime());

	}

	/**
	 * 
	 * Test Tag Related
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

	}

	@Override
	public void onTestFailure(ITestResult arg0) {

	}

	@Override
	public void onTestSkipped(ITestResult arg0) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

}
