package br.com.mbarros.listeners;

import br.com.mbarros.PlaywrightManager;
import org.testng.*;

import java.io.File;
import java.util.Objects;

import static org.testng.Assert.fail;

/**
 * This class represents a DefaultListener that implements both ISuiteListener and IInvokedMethodListener interfaces.
 * It handles tasks such as deleting Allure report files, reading Maven parameters, modifying test suite thread counts and managing Playwright instances.
 */
public class DefaultListener implements ISuiteListener, IInvokedMethodListener {

    private Integer featureThreadCount;
    private Integer scenarioThreadCount;

    /**
     * Performs actions before the test suite starts.
     *
     * @param suite The test suite object.
     */
    @Override
    public void onStart(ISuite suite) {
        deleteAllureReportFiles();
        deleteAllureReportFilesFolderReports();
        readMavenParameters();

        if (Objects.nonNull(featureThreadCount)) {
            suite.getXmlSuite().setThreadCount(featureThreadCount);
        }

        if (Objects.nonNull(scenarioThreadCount)) {
            suite.getXmlSuite().setDataProviderThreadCount(scenarioThreadCount);
        }
    }

    @Override
    public void onFinish(ISuite suite) {
    }

    /**
     * Performs actions before a test method is invoked.
     *
     * @param method     The IInvokedMethod object representing the method being invoked.
     * @param testResult The ITestResult object representing the test result.
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            PlaywrightManager.getPlaywright();
        }
    }

    /**
     * Performs actions after a test method is invoked.
     *
     * @param method     The IInvokedMethod object representing the method being invoked.
     * @param testResult The ITestResult object representing the test result.
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            PlaywrightManager.closeAPIRequestContext();
            PlaywrightManager.closePlaywright();
        }
    }

    /**
     * Deletes all files in the "allure-results" folder.
     */
    public static void deleteAllureReportFiles() {
        File file = new File("allure-results/");

        if (file.exists()) {
            for (File subfile : file.listFiles()) {
                subfile.delete();
            }
        }
    }

    /**
     * Deletes all files in the "reports/allure-results" folder.
     */
    public static void deleteAllureReportFilesFolderReports() {
        File file = new File("reports/allure-results/");

        if (file.exists()) {
            for (File subfile : file.listFiles()) {
                subfile.delete();
            }
        }
    }

    /**
     * Reads Maven parameters and sets the featureThreadCount and scenarioThreadCount values.
     */
    private void readMavenParameters() {
        try {
            if (System.getProperty("featureThreadCount") != null) {
                featureThreadCount = Integer.parseInt(System.getProperty("featureThreadCount"));
            }
            if (System.getProperty("scenarioThreadCount") != null) {
                scenarioThreadCount = Integer.parseInt(System.getProperty("scenarioThreadCount"));
            }
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
