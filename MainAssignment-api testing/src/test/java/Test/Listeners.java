package Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Test.ExtentReportManager;
public class Listeners implements ITestListener{
    Logger logger = LogManager.getLogger(Listeners.class.getName());
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;

    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReports.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result){
        extentTest.pass("Status : Pass");
        logger.info(result.getName()+" : Successful");
    }

    @Override
    public void onTestFailure(ITestResult result){
        extentTest.fail("Status : Fail");
        logger.info(result.getName()+ " : Fail");
    }
    @Override
    public void onTestSkipped(ITestResult result){
        logger.info(result.getName()+" : Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onStart(ITestContext context) {
        extentReports = ExtentReportManager.Extentreporter();
        logger.info(context.getName()+" : Started");
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
        logger.info("Test is completed");
    }
}
