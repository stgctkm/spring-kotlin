package spring.kotlin.jdbc.application

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import spring.kotlin.jdbc.domain.Customer


@Service
@Transactional
class JdbcTemplateService (private val jdbcTemplate : JdbcTemplate) : CustomerService {
    override fun all(): Collection<Customer> =
            jdbcTemplate.query("select * from CUSTOMERS") {
                rs, _ -> Customer(rs.getString("NAME"), rs.getLong("ID"))
            }

    override fun byId(id: Long): Customer? = jdbcTemplate.queryForObject("select * from CUSTOMERS where ID = ?", id) {
        rs, _ -> Customer(rs.getString("NAME"), rs.getLong("ID"))
    }
    override fun insert(customer: Customer) {
        jdbcTemplate.execute("insert into CUSTOMERS (NAME) values (?)") {
            it.setString(1, customer.name)
            it.execute()
        }
    }
}