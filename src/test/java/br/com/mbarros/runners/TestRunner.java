package br.com.mbarros.runners;

import br.com.mbarros.listeners.DefaultListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@CucumberOptions(
        tags = "@AllScenarios-EN",
        features = {"src/test/resources/features/"},
        glue = {"classpath:br/com/mbarros"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {
                "rerun:target/rerun/failed_scenarios.txt",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        })
@Listeners(DefaultListener.class)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
