package org.cameek.spring_excel.db

import jakarta.annotation.PostConstruct
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.time.LocalDate
import kotlin.random.Random

@Component
class DataInitializer(private val jdbcTemplate: JdbcTemplate) {

    @PostConstruct
    fun initialize() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS metrics(date TEXT, metric REAL)")

        jdbcTemplate.execute("DELETE FROM metrics")

        val now = LocalDate.now()
        for (i in 0..29) {
            jdbcTemplate.update(
                "INSERT INTO metrics(date, metric) VALUES (?,?)",
                now.minusDays(i.toLong()).toString(),
                Random.nextDouble(0.0, 100.0)
            )
        }
    }
}
