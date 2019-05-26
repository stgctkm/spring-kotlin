package spring.kotlin.reactive.domain

import org.springframework.data.mongodb.core.mapping.Document




@Document
data class Customer (val id : String? = null, val name : String)