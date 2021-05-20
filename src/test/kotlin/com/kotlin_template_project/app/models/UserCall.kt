package com.kotlin_template_project.app.models

import io.restassured.RestAssured

class UserCall {
    var username: String? = null

    fun getFindAllUsersURL() = RestAssured.baseURI + RestAssured.basePath;

    fun getFindByUsernameURL(username: String) = RestAssured.baseURI + RestAssured.basePath + username;
}