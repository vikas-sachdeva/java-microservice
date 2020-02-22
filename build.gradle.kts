import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.springframework.cloud.contract.verifier.plugin.ContractVerifierExtension

plugins {
    java
    idea
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("org.springframework.cloud.contract") version "2.2.1.RELEASE"
    id("org.asciidoctor.convert") version "1.5.6"
    // id("spring-cloud-contract-gradle-plugin") version "2.2.1.RELEASE"

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter")
    implementation("org.springframework.boot", "spring-boot-starter-webflux")
    implementation("org.springframework.boot", "spring-boot-starter-data-mongodb-reactive")

    implementation("de.flapdoodle.embed", "de.flapdoodle.embed.mongo", "2.2.0")

    testImplementation("org.springframework.boot", "spring-boot-starter-test") {
        exclude("junit", "junit")
    }
    testImplementation("io.projectreactor", "reactor-test")
    testImplementation("io.rest-assured", "spring-web-test-client")
    testImplementation("org.springframework.cloud", "spring-cloud-contract-verifier")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine")

    testImplementation("org.junit.jupiter:junit-jupiter-api")

    implementation("com.sun.xml.bind:jaxb-osgi:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.0")
    implementation("javax.xml.bind:jaxb-api:2.3.0")

    testImplementation("org.springframework.restdocs", "spring-restdocs-webtestclient", "2.0.4.RELEASE")

}

dependencyManagement {

    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:2.2.1.RELEASE") {
            bomProperty("kotlin.version", "1.3.61")
        }
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.RELEASE")
        mavenBom("org.springframework.cloud:spring-cloud-contract-dependencies:2.2.0.RELEASE")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    maxHeapSize = "1G"
    failFast = false
    defaultCharacterEncoding = "UTF-8"

    testLogging {
        exceptionFormat = FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

configure<ContractVerifierExtension> {
    setBaseClassForTests("jsample.AppBaseClass")
    setTestMode("WEBTESTCLIENT")
    setTestFramework("JUNIT5")

}

tasks.withType<AsciidoctorTask>().configureEach {

    file("$buildDir/generated-snippets").also {
        if (!it.exists()) mkdir(it)
        inputs.dir(it)
    }
    sources(delegateClosureOf<PatternSet> { include("index.adoc") })

    attributes = mapOf("allow-uri-read" to "", "data-uri" to "")
    options(attributes)

    doLast {
        copy {
            from("$buildDir/asciidoc/html5")
            into("$projectDir/src/main/resources/static")
            include("index.html")
        }
    }
}