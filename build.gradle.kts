plugins {
    application
    java
}

group = "kr.co.zestech"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.influxdb:influxdb-client-java:6.9.0")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
    //4.0.3 working for java 1.8
    implementation("com.zaxxer:HikariCP:4.0.3")

    //for hikari pool logging
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

application {
    mainClass.set("com.zes.device.ZES_DeviceApplication")
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "processResources", "classes")) // We need this for Gradle optimization to work
        archiveClassifier.set("standalone") // Naming the jar
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
}