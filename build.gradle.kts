import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import de.undercouch.gradle.tasks.download.Download
import de.undercouch.gradle.tasks.download.Verify
import org.gradle.internal.impldep.org.apache.tools.zip.UnsupportedZipFeatureException
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.js.dce.InputResource
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.spring") version "1.5.0"
    kotlin("plugin.jpa") version "1.5.0"
    kotlin("kapt") version "1.5.0"
    id("io.qameta.allure") version "2.8.1"
    id("de.undercouch.download") version "4.1.1"
}

allure {
    version = "2.13.9"
    autoconfigure = true
    aspectjweaver = true
    resultsDir = file("allure-results")
    reportDir = file("allure-report")
    allureJavaVersion = "2.13.9"
}

val unitTestTask = tasks.register<Test>("unit-tests") {
    useJUnitPlatform()
    testLogging {
        // set options for log level LIFECYCLE
        events = mutableSetOf(TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT)

        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true

        // set options for log level DEBUG and INFO
        debug {
            events = mutableSetOf(TestLogEvent.STARTED,
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_ERROR,
                TestLogEvent.STANDARD_OUT)
            exceptionFormat = TestExceptionFormat.FULL
        }
        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat
    }
}

val integrationTestTask = tasks.register<Test>("integration-tests") {
    val testNGOptions = closureOf<TestNGOptions> {
        suites("src/test/resources/testng.xml")
    }

    useTestNG(testNGOptions)
    testLogging.showStandardStreams = true

    dependsOn("prepareWebDrivers")
}

val chromedriverVersion = project.findProperty("chromedriverVersion") ?: "2.36"
val geckodriverVersion = project.findProperty("geckodriverVersion") ?: "v0.18.0"


task<Download>("wget-chromedriver-mac64-zip") {
    src("https://chromedriver.storage.googleapis.com/${chromedriverVersion}/chromedriver_mac64.zip")
    dest("src/test/resources")
    overwrite(false)
}
task<Verify>("verify-chromedriver-mac64-zip") {
    src("src/test/resources/chromedriver_mac64.zip")
    algorithm("MD5")
    checksum("312cd778e385a255c60caed5ffbaf6e5")
    dependsOn("wget-chromedriver-mac64-zip")
}
task<Copy>("unzip-chromedriver-mac64"){
    from(zipTree(File("src/test/resources/chromedriver_mac64.zip")))
    into(File("src/test/resources/chromedriver/macos"))
    dependsOn("verify-chromedriver-mac64-zip")
}


task<Download>("wget-chromedriver-linux64-zip") {
    src("https://chromedriver.storage.googleapis.com/${chromedriverVersion}/chromedriver_linux64.zip")
    dest("src/test/resources")
    overwrite(false)
}
task<Verify>("verify-chromedriver-linux64-zip") {
    src("src/test/resources/chromedriver_linux64.zip")
    algorithm("MD5")
    checksum("24d2004a0b6c9fb4fcd74d1966b0ca6e")
    dependsOn("wget-chromedriver-linux64-zip")
}
task<Copy>("unzip-chromedriver-linux64"){
    from(zipTree(File("src/test/resources/chromedriver_linux64.zip")))
    into(File("src/test/resources/chromedriver/linux64"))
    dependsOn("verify-chromedriver-linux64-zip")
}


task<Download>("wget-geckodriver-mac64-zip") {
    src("https://github.com/mozilla/geckodriver/releases/download/${geckodriverVersion}/geckodriver-${geckodriverVersion}-macos.tar.gz")
    dest("src/test/resources/geckodriver-${geckodriverVersion}-macos.tar.gz")
    overwrite(false)
}
task<Verify>("verify-geckodriver-mac64-zip") {
    src("src/test/resources/geckodriver-${geckodriverVersion}-macos.tar.gz")
    algorithm("MD5")
    checksum("79cf3050cc942cdff6edbc7605d05ef2")
    dependsOn("wget-geckodriver-mac64-zip")
}
task<Copy>("unzip-geckodriver-mac64"){
    from(tarTree(File("src/test/resources/geckodriver-${geckodriverVersion}-macos.tar.gz")))
    into(File("src/test/resources/geckodriver/macos"))
    dependsOn("verify-geckodriver-mac64-zip")
}

task<Download>("wget-geckodriver-linux64-zip") {
    src("https://github.com/mozilla/geckodriver/releases/download/${geckodriverVersion}/geckodriver-${geckodriverVersion}-linux64.tar.gz")
    dest("src/test/resources/geckodriver-${geckodriverVersion}-linux64.tar.gz")
    overwrite(false)
}
task<Verify>("verify-geckodriver-linux64-zip") {
    src("src/test/resources/geckodriver-${geckodriverVersion}-linux64.tar.gz")
    algorithm("MD5")
    checksum("4ccb56fb3700005c9f9188f84152f21a")
    dependsOn("wget-geckodriver-linux64-zip")
}
task<Copy>("unzip-geckodriver-linux64"){
    from(tarTree(File("src/test/resources/geckodriver-${geckodriverVersion}-linux64.tar.gz")))
    into(File("src/test/resources/geckodriver/linux64"))
    dependsOn("verify-geckodriver-linux64-zip")
}


task("prepareWebDrivers") {
    dependsOn("unzip-chromedriver-mac64")
    dependsOn("unzip-chromedriver-linux64")
    dependsOn("unzip-geckodriver-mac64")
    dependsOn("unzip-geckodriver-linux64")
}


group = "com.kotlin_template_project"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.mockk:mockk:1.11.0")
    kapt("org.springframework.boot:spring-boot-configuration-processor")



    implementation("io.qameta.allure:allure-generator:2.13.9")
    implementation("io.qameta.allure:allure-testng:2.13.9")

    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("com.typesafe:config:1.4.1")

    implementation("io.cucumber:cucumber-java:6.10.3")
    implementation("io.cucumber:cucumber-testng:6.10.3")

    implementation("org.testng:testng:7.4.0")

    // http://www.unitils.org/tutorial-reflectionassert.html
    implementation("org.unitils:unitils-core:3.4.6")

    implementation("com.github.rholder:guava-retrying:2.0.0")

    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
    implementation("org.seleniumhq.selenium:selenium-support:3.141.59")

    implementation("ru.yandex.qatools.ashot:ashot:1.5.4")

    implementation(kotlin("stdlib", "1.5.0"))

    implementation("org.jetbrains.kotlin:kotlin-test:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

    testImplementation("io.rest-assured:rest-assured:4.2.0")
    testImplementation("io.rest-assured:rest-assured-all:4.2.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}


repositories {
    mavenCentral()
    jcenter()
}


kotlin {
    experimental.coroutines = Coroutines.ENABLE
}