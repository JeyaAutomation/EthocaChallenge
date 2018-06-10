package common;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		System.out.println("TEST STARTED: " + tr.getTestClass().getName() + "#" + tr.getMethod().getMethodName());
	}

	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		// TODO:
	}

	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		System.out.println("TEST SKIPPED:" + tr.getTestClass().getName() + "#" + tr.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		System.out.println("TEST PASSED:" + tr.getTestClass().getName() + "#" + tr.getMethod().getMethodName());
	}

	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
	}

}
