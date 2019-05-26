package spring.kotlin.reactive

import org.reactivestreams.Publisher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.toFlux
import spring.kotlin.reactive.domain.Customer
import spring.kotlin.reactive.domain.CustomerRepository

@SpringBootApplication
class ReactiveApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(ReactiveApplication::class.java)
            .initializers(beans{
                bean {
                    val customerRepository = ref<CustomerRepository>()
                    val customers : Publisher<Customer> = listOf("margelett", "michel", "Matt", "Michelle")
                            .toFlux()
                            .map { Customer(name = it) }
                            .flatMap { customerRepository.save(it) }

                    customerRepository.deleteAll()
                            .thenMany(customers)
                            .thenMany(customerRepository.findAll())
                            .subscribe { println(it)}
                }
                bean {
                    router {
                        val customerRepository = ref<CustomerRepository>()
                        GET("/customers") { ServerResponse.ok().body(customerRepository.findAll()) }
                        GET("/customers/{id}") { ServerResponse.ok().body(customerRepository.findById(it.pathVariable("id"))) }
                    }
                }
//                bean {
//                    gateway {
//                        route {
//                            id("blog")lb
//                            predidate(path("blog")) or path("/atom")
//                            uri("http://spring.io:80/blog.atom")
////                            uri("lb://foo-service")
//                        }
//                    }
//                }
            })
            .run(*args)

}
