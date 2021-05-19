package qa.example

import io.cucumber.testng.AbstractTestNGCucumberTests
import io.cucumber.testng.CucumberOptions
import org.testng.annotations.Test

@Test
@CucumberOptions(
        plugin = ["pretty"]
)
class CucumberRunner : AbstractTestNGCucumberTests()
