package com.kotlin_template_project.app

import java.util.*

class TestConfiguration() {
    var host: String? = null
    var hostHealthCheck: String? = null
    var defaultTestConfiguration: ResourceBundle = ResourceBundle.getBundle("configuration")

    init {
        host = if (System.getProperty("host") != null) {
            System.getProperty("host")
        } else {
            defaultTestConfiguration.getString("host")
        }
        hostHealthCheck = if (System.getProperty("hostHealthCheck") != null) {
            System.getProperty("hostHealthCheck")
        } else {
            defaultTestConfiguration.getString("hostHealthCheck")
        }
    }
}