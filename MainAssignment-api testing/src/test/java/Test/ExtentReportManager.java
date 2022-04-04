package Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtentReportManager {

    public static ExtentReports Extentreporter() {
        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("C:/Users/tdhurwe/Documents/API testing/MainAssignment-api testing/Extent Report/extentReport.html");
        extentReports.attachReporter(reporter);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle("API extent report");
        reporter.config().setReportName("Extent Report");
        return extentReports;
    }
}
