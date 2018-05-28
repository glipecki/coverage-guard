package net.lipecki.covgrd.coverageguard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoverageGuardApplication

fun main(args: Array<String>) {
    runApplication<CoverageGuardApplication>(*args)
}
