package com.kotlin_template_project.app.steps

import com.kotlin_template_project.app.TestConfiguration
import com.kotlin_template_project.app.utils.RestUtil.getJsonPath
import io.restassured.path.json.JsonPath
import io.restassured.response.Response


class GenericStepDefinition {
    var testConfiguration: TestConfiguration? = null

    init {
        testConfiguration = TestConfiguration()
    }

    fun getEntityFromResponse(response: Response, entity: String?): ArrayList<*>? {
        val json: JsonPath = getJsonPath(response)
        return json.get(entity)
    }
}