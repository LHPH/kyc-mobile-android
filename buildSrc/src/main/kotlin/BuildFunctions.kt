package com.kyc.mobile.buildsrc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.gradle.api.GradleException
import org.gradle.api.Project
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Properties

data class PropertyData(
    val key: String,
    val value: String,
    val type: String
)

data class AppConfig(
    @JsonProperty("app-config")
    val appConfig: List<PropertyData>
)

fun readYamlConfig(project: Project, filePath: String): AppConfig {
    val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
	
	val configFile = project.file(filePath)
	if(configFile.exists()){
		return mapper.readValue(configFile, AppConfig::class.java)
	}
    throw GradleException("Properties file not found at: $filePath")
}

fun loadProperties(project: Project, path: String): Properties {
    val properties = Properties()
    val propertiesFile = project.file(path)
    if (propertiesFile.exists()) {
        propertiesFile.bufferedReader().use { reader ->
            properties.load(reader)
        }
    } else {
        throw GradleException("Properties file not found at: $path")
    }
    return properties
}