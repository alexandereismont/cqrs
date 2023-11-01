package com.cqrs.kotlincqrs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinCqrsApplication

fun main(args: Array<String>) {
	runApplication<KotlinCqrsApplication>(*args)
}
