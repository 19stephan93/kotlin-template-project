package com.kotlin_template_project.app.steps

import com.kotlin_template_project.app.TestConfiguration
import com.kotlin_template_project.app.models.UserCall
import com.kotlin_template_project.app.utils.RestConfig.setBasePath
import com.kotlin_template_project.app.utils.RestConfig.setBaseURI
import com.kotlin_template_project.app.utils.RestUtil
import com.kotlin_template_project.app.utils.RestUtil.getResponse
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.qameta.allure.Step
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import org.assertj.core.api.Assertions
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


class UserStepDefinition {
    private var userCall: UserCall? = null

    private var allUsersResponse: Response? = null

    init {
        userCall = UserCall()

        setBaseURI(TestConfiguration().host)
        setBasePath("userPath")
    }

    @Step
    @When("^I request all users list$")
    fun iRequestAllUsersList() {
        allUsersResponse = getResponse(userCall?.getFindAllUsersURL())
    }

    @Step
    @Then("^I receive http status \"([^\"]*)\"$")
    fun iReceiveHttpStatus(httpStatus: String) {
        RestUtil.checkStatus(allUsersResponse!!, httpStatus);
    }

    @Step
    @Then("^I receive content type \"([^\"]*)\"$")
    fun iReceiveContentType(contentType: String) {
        RestUtil.checkContentType(allUsersResponse!!, contentType)
    }

    @Step
    @Then("^I receive the following user: \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    fun iReceiveTheFollowingUser(username: String, firstName: String, lastName: String) {
        val path: JsonPath = allUsersResponse!!.body.jsonPath()
        val list: List<LinkedHashMap<String, JvmType.Object>> = path.getList("")

        var found = false
        for (elem in list) {
            if (elem["username"].toString() == username &&
                elem["firstname"].toString() == firstName &&
                elem["lastname"].toString() == lastName) {
                found = true
                break
            }
        }
        Assertions.assertThat(found).isEqualTo(true)
    }
}