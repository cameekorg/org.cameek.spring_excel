package org.cameek.spring_excel.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class AppConfig {

    @Bean
    fun dataSource(): DataSource {
        return HikariDataSource().apply {
            jdbcUrl = "jdbc:sqlite:metrics.db"
            driverClassName = "org.sqlite.JDBC"
        }
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}
