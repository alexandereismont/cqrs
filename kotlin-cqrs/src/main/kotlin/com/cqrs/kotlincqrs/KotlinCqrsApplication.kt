package com.cqrs.kotlincqrs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class] )
class KotlinCqrsApplication

fun main(args: Array<String>) {
	runApplication<KotlinCqrsApplication>(*args)
}
