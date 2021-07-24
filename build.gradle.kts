import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
	kotlin("plugin.jpa") version "1.5.21"

	id("application")
	id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "apps_spring"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

sourceSets.main {
	java.srcDirs("src/main/java", "src/main/kotlin")
}

application {
	mainClass.set("javafx_multiple.MyLauncher")
}

// for a module
//application {
//	mainModule.set("org.gradle.sample.app") // name defined in module-info.java
//	mainClass.set("org.gradle.sample.Main")
//}

javafx {
	version = "15"
	modules("javafx.controls", "javafx.graphics", "javafx.fxml")
}

val junitJupiterVersion  = "5.6.1"

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.session:spring-session-core")
	runtimeOnly("com.h2database:h2")

//	// commented for a soft startup
	//implementation("org.springframework.boot:spring-boot-starter-data-rest")
//	implementation("org.springframework.boot:spring-boot-starter-webflux")
//	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
//	implementation("org.springframework.boot:spring-boot-starter-websocket")
//	runtimeOnly("mysql:mysql-connector-java")
//	compileOnly("org.projectlombok:lombok")
//	annotationProcessor("org.projectlombok:lombok")
	//
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")



	// https://mvnrepository.com/artifact/io.netty/netty-all
	implementation("io.netty:netty-all:4.1.45.Final")
	// https://mvnrepository.com/artifact/com.google.code.gson/gson
	implementation("com.google.code.gson:gson:2.8.6")

	// ==== REACT_PROGRAMMING LIBS
	implementation("io.reactivex.rxjava2:rxjava:2.2.0")
	implementation("io.projectreactor.addons:reactor-adapter:3.0.2.RELEASE");
	implementation("io.reactivex.rxjava3:rxjava:3.0.7")

	// TornadoFX
	implementation("no.tornado:tornadofx:1.7.20")

	// === LOG
	implementation("org.apache.logging.log4j:log4j-api:2.13.3")
	implementation("org.apache.logging.log4j:log4j-core:2.13.3")

	// ControlsFX
	// https://mvnrepository.com/artifact/org.controlsfx/controlsfx
	implementation("org.controlsfx:controlsfx:11.0.3")

	// FlexboxFX
	// https://mvnrepository.com/artifact/com.dukescript.amaronui.layouts/jfxflexbox
	implementation("com.dukescript.amaronui.layouts:jfxflexbox:0.6")


	// FontAwesome for tornadoFX

	/* === old repository not working ===
//  compile 'de.jensd:fontawesomefx:8.9'
implementation 'de.jensd:fontawesomefx-fontawesome:4.7.0-11'
implementation 'de.jensd:fontawesomefx-commons:11.0'
    // FontawesomeFX
    implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-11")
    implementation("de.jensd:fontawesomefx-commons:11.0")
    implementation("de.jensd:fontawesomefx-materialdesignfont:1.7.22-11")
    implementation("de.jensd:fontawesomefx-materialicons:2.2.0-11")
    implementation("de.jensd:fontawesomefx-materialstackicons:2.1-11")
    implementation("de.jensd:fontawesomefx-octicons:4.3.0-11")
    implementation("de.jensd:fontawesomefx-weathericons:2.0.10-11")
// https://mvnrepository.com/artifact/de.jensd/fontawesomefx-commons
     */

	// https://mvnrepository.com/artifact/de.jensd/fontawesomefx-fontawesome
	implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
	// https://mvnrepository.com/artifact/de.jensd/fontawesomefx-commons
	implementation("de.jensd:fontawesomefx-commons:9.1.2")
	implementation("de.jensd:fontawesomefx-materialdesignfont:2.0.26-9.1.2")
	implementation("de.jensd:fontawesomefx-materialicons:2.2.0-9.1.2")
	implementation("de.jensd:fontawesomefx-materialstackicons:2.1-5-9.1.2")
	implementation("de.jensd:fontawesomefx-octicons:4.3.0-9.1.2")
	implementation("de.jensd:fontawesomefx-weathericons:2.0.10-9.1.2")

//    // https://mvnrepository.com/artifact/commons-logging/commons-logging
//    compile group: 'commons-logging', name: 'commons-logging', version: '1.2'
//    // https://mvnrepository.com/artifact/dom4j/dom4j
//    compile group: 'dom4j', name: 'dom4j', version: '1.6.1'
//    // https://mvnrepository.com/artifact/org.apache.pdfbox/fontbox
//    compile group: 'org.apache.pdfbox', name: 'fontbox', version: '2.0.19'
//    // https://mvnrepository.com/artifact/org.apache.geronimo.specs/geronimo-stax-api_1.0_spec
//    compile group: 'org.apache.geronimo.specs', name: 'geronimo-stax-api_1.0_spec', version: '1.0.1'
//// https://mvnrepository.com/artifact/com.itextpdf/itext-xtra
//    compile group: 'com.itextpdf', name: 'itext-xtra', version: '5.5.13.1'
//// https://mvnrepository.com/artifact/org.apache.pdfbox/jempbox
//    compile group: 'org.apache.pdfbox', name: 'jempbox', version: '1.8.16'
//// https://mvnrepository.com/artifact/log4j/log4j
//    compile group: 'log4j', name: 'log4j', version: '1.2.17'
//// https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox
//    compile group: 'org.apache.pdfbox', name: 'pdfbox', version: '2.0.19'
//// https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox-app
//    compile group: 'org.apache.pdfbox', name: 'pdfbox-app', version: '2.0.19'
//
//    // microsoft format files
//// https://mvnrepository.com/artifact/org.apache.poi/poi
//    compile group: 'org.apache.poi', name: 'poi', version: '4.1.2'
//    // https://mvnrepository.com/artifact/org.apache.poi/poi-examples
//    compile group: 'org.apache.poi', name: 'poi-examples', version: '4.1.2'
//// https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml
//    compile group: 'org.apache.poi', name: 'poi-ooxml', version: '4.1.2'
//// https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml-schemas
//    compile group: 'org.apache.poi', name: 'poi-ooxml-schemas', version: '4.1.2'
//// https://mvnrepository.com/artifact/org.apache.poi/poi-scratchpad
//    compile group: 'org.apache.poi', name: 'poi-scratchpad', version: '4.1.2'
//// https://mvnrepository.com/artifact/org.apache.xmlbeans/xmlbeans
//    compile group: 'org.apache.xmlbeans', name: 'xmlbeans', version: '3.1.0'

	/////////////////////
	// junit in action
	////////////////////
//    // https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-server
	//   compile group: 'org.eclipse.jetty', name: 'jetty-server', version: '9.4.27.v20200227'

	// https://mvnrepository.com/artifact/org.springframework/spring-context
//    implementation("org.springframework:spring-context:${springVersion}")
//    testImplementation("org.springframework:spring-test:${springVersion}")


	// https://mvnrepository.com/artifact/commons-logging/commons-logging
	implementation("commons-logging:commons-logging:1.2")

//// https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
	testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
	//testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
	// compile("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")


// params
	// https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params
	testImplementation("org.junit.jupiter:junit-jupiter-params:${junitJupiterVersion}")

	testImplementation("org.hamcrest:hamcrest:2.2")
	// https://mvnrepository.com/artifact/io.mockk/mockk
	testImplementation("io.mockk:mockk:1.9.3")


//    // https://mvnrepository.com/artifact/io.cucumber/cucumber-java
//    testImplementation("io.cucumber:cucumber-java:5.6.0")
//
//    // https://mvnrepository.com/artifact/io.cucumber/cucumber-junit
//    testImplementation("io.cucumber:cucumber-junit:5.6.0")
//


	// dependencies used by examples in this project
	testImplementation("com.h2database:h2:1.4.197")

	// TESTS
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
