package spring.kotlin.jdbc.application

import spring.kotlin.jdbc.domain.Customer


interface CustomerService {
    fun all() : Collection<Customer>
    fun byId(id : Long) : Customer?
    fun insert(customer : Customer)
}