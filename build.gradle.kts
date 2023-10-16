plugins {
	java
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("plugin.lombok") version "1.8.10"
	idea
}

group = "com.pichincha.prueba"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.h2database:h2:2.2.224")
	implementation("org.projectlombok:lombok")
	implementation("org.hibernate:hibernate-core:5.4.6.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
