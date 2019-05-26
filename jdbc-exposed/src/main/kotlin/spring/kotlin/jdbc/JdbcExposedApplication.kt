package spring.kotlin.jdbc

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import spring.kotlin.jdbc.application.CustomerService
import spring.kotlin.jdbc.domain.Customer

@SpringBootApplication
class JdbcExposedApplication

fun main(args: Array<String>) {
//	runApplication<JdbcApplication>(*args)
    SpringApplicationBuilder()
            .initializers(beans {
                bean {
                    ApplicationRunner {
                        val customerService = ref<CustomerService>()
                        arrayOf("mario", "cornelia", "Andrew", "Tammie")
                                .map { Customer(name = it) }
                                .forEach { customerService.insert(it) }
                        customerService.all().forEach { println(it) }
                    }
                }
            })
            .sources(JdbcExposedApplication::class.java)
            .run(*args)
}
