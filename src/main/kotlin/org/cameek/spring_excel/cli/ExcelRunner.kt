package org.cameek.spring_excel.cli

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream

@Component
class ExcelRunner(
    val jdbcTemplate: JdbcTemplate,
    @Value("\${excel.template}") val templatePath: String,
    @Value("\${excel.output}") val outputPath: String,
    @Value("\${report.start-date}") val startDate: String,
    @Value("\${report.end-date}") val endDate: String
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val workbook = XSSFWorkbook(File(templatePath).inputStream())
        val sheet = workbook.getSheetAt(0)

        val data = jdbcTemplate.queryForList(
            "SELECT date, metric FROM metrics WHERE date BETWEEN ? AND ?",
            startDate, endDate
        )

        var rowIdx = 1
        data.forEach {
            val row = sheet.createRow(rowIdx++)
            row.createCell(0).setCellValue(it["date"].toString())
            row.createCell(1).setCellValue(it["metric"].toString().toDouble())
        }

        workbook.use {
            FileOutputStream(outputPath).use { out -> workbook.write(out) }
        }
        println("Excel file generated: $outputPath")
    }
}
