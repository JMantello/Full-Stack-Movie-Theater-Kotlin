package com.jmantello.movietheaterserver.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // All endpoints accessible by...
            .allowedOrigins("http://localhost:3000") // these origins
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowCredentials(true) // Allow cookies
    }
}