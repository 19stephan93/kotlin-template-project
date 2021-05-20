package com.kotlin_template_project.app.utils

import io.restassured.RestAssured
import java.util.*


object RestConfig {
    private val endpoints: ResourceBundle = ResourceBundle.getBundle("endpoints")
    fun setBaseURI(environment: String?) {
        RestAssured.baseURI = environment
    }

    fun setBasePath(endpoint: String?) {
        RestAssured.basePath = endpoints.getString(endpoint)
    }
}