package spring.kotlin.reactive.domain


import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CustomerRepository : ReactiveMongoRepository<Customer, String>