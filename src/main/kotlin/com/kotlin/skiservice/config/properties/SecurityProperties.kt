package com.kotlin.skiservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "security")
class SecurityProperties {
    var secret: String = ""
}