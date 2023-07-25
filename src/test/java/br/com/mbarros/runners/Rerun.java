package br.com.mbarros.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"@target/rerun/failed_scenarios.txt"},
        glue = {"classpath:br/com/mbarros"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {
                "rerun:target/rerun/failed_scenarios.txt",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
        })
public class Rerun extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}