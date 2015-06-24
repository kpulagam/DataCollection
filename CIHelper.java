package com.paypal.test.gops.admin.listeener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONException;
import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class CIHelper {

	/**
	 * @param args
	 */

	protected synchronized void collectCIData(IInvokedMethod currentTestRun,
			ITestResult testResult, ClassData setdata, String testClassStatus)
			throws JSONException {

		boolean hasTestCaseStarted = false;

		String tmpClassName = currentTestRun.getTestMethod().getRealClass()
				.getName();

		if (setdata == null) {
			throw new NullPointerException("The object is null");
		} else {

			if (!setdata.getHasTestRunCompleted()) {
				if (setdata.getAllClassMethodMap().containsKey(tmpClassName)) {
					if (setdata
							.getAllClassMethodMap()
							.get(tmpClassName)
							.containsKey(
									currentTestRun.getTestMethod()
											.getMethodName())) {

						setdata.getAllClassMethodMap()
								.get(tmpClassName)
								.put(currentTestRun.getTestMethod()
										.getMethodName(),
										testResult.getStatus());

						

					}

				} else {

					
					System.out.println("I am in");

				}

			}

		}

		for (String className : setdata.getAllClassMethodMap().keySet()) {

			int i = 1;
			int j = 0;
			for (Entry<String, Integer> methodName : setdata
					.getAllClassMethodMap().get(className).entrySet()) {
				j = j + methodName.getValue();

				if (j == 0) {
					testClassStatus = "Not Started";

				}

				else {

					testClassStatus = "In Progress";
					hasTestCaseStarted = true;
				}
				i = i * methodName.getValue();

			}

			if (hasTestCaseStarted) {
				if (i == 1)
					testClassStatus = "Passed";
				else if (i > 1)
					testClassStatus = "Failed";
			}

			if (setdata.getAllClassMethodMap().get(className).containsValue(3)) {
				testClassStatus = "Failed";

			}

			setdata.setTestClassResultSet(className, testClassStatus);

		}

	}

	protected synchronized List<String> setMethodStatus(ClassData setdata,
			String className) {
		List<String> newList = new ArrayList<String>();

		for (Entry<String, Integer> methodName : setdata.getAllClassMethodMap()
				.get(className).entrySet()) {

			switch (methodName.getValue()) {

			case 0:
				newList.add("Method: " + methodName.getKey()
						+ " Status: Not Started");
				break;
			case 1:
				newList.add("Method: " + methodName.getKey()
						+ " Status: Passed");
				break;
			case 2:
				newList.add("Method: " + methodName.getKey()
						+ " Status: Failed");
				break;
			case 3:
				newList.add("Method: " + methodName.getKey()
						+ " Status: Skipped");
				break;
			default:
				newList.add("Method: " + methodName.getKey()
						+ " Status: Failed");

			}

		}
		return newList;

	}

	protected void createClassMethodMap(
			HashMap<String, Integer> listOfAllMethods,
			List<ITestNGMethod> initialMethods, ISuite suite, ClassData setdata) {

		initialMethods = suite.getAllMethods();

		listOfAllMethods = new HashMap<String, Integer>();

		String firstMethodName = initialMethods.get(0).toString().split(",")[0]
				.split("\\.")[1].split("\\(")[0];
		String firstClassName = initialMethods.get(0).toString().split(",")[1]
				.split(":")[1].split("@")[0];

		listOfAllMethods.put(firstMethodName, 0);
		setdata.setAllClassMethodMap(firstClassName, listOfAllMethods);

		int i = 1;
		do {

			String methodName = initialMethods.get(i).toString().split(",")[0]
					.split("\\.")[1].split("\\(")[0];
			String className = initialMethods.get(i).toString().split(",")[1]
					.split(":")[1].split("@")[0];
			if (setdata.getAllClassMethodMap().containsKey(className)) {

				listOfAllMethods = setdata.getAllClassMethodMap()
						.get(className);
				listOfAllMethods.put(methodName, 0);
				setdata.setAllClassMethodMap(className, listOfAllMethods);

			} else if (!setdata.getAllClassMethodMap().containsKey(className)) {

				listOfAllMethods = new HashMap<String, Integer>();
				listOfAllMethods.put(methodName, 0);
				setdata.setAllClassMethodMap(className, listOfAllMethods);

			}

			i++;

		}

		while (i < initialMethods.size());

	}

}
