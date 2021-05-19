package com.kotlin_template_project.app.utils

import io.restassured.response.Response
import io.restassured.RestAssured.get
import io.restassured.path.json.JsonPath
import org.assertj.core.api.Assertions
import java.util.*


object RestUtil {
    fun getResponse(apiUrl: String?): Response {
        return get(apiUrl)
    }

    fun getJsonPath(response: Response): JsonPath {
        val json: String = response.asString()
        return JsonPath(json)
    }

    private fun identifyStatusCode(responseMessage: String): Int {
        val status: Int
        when (responseMessage.lowercase()) {
            "ok" -> status = 200
            "up" -> status = 200
            "bad request" -> status = 400
            "not found" -> status = 404
            else -> status = 0
        }
        return status
    }

    fun checkStatus(response: Response, statusMessage: String) {
        Assertions.assertThat(response.statusCode)
            .isEqualTo(identifyStatusCode(statusMessage))
            .overridingErrorMessage("The actual status code is: " + response.statusCode
                .toString() + " but the expected status code is: " + identifyStatusCode(statusMessage))
    }

    fun checkHealthStatus(response: Response, expectedStatus: String) {
        Assertions.assertThat(response.statusCode).isEqualTo(200)
            .overridingErrorMessage("The health request failed!")

        val healthStatusJson: JsonPath = getJsonPath(response)
        val actualStatus: String = healthStatusJson.get("status")

        Assertions.assertThat(actualStatus).isEqualTo(expectedStatus)
            .overridingErrorMessage("The health status is: $actualStatus but the expected one is: $expectedStatus")
    }

    fun checkContentType(response: Response, expectedContentType: String) {
        Assertions.assertThat(response.contentType).isEqualTo(expectedContentType)
    }
}
