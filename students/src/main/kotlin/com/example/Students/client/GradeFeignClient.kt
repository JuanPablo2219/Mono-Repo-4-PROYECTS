package com.example.Students.client

import com.example.Students.dto.GradeDto
import jakarta.persistence.Id
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("GRADE-MS")
interface GradeFeignClient {
    @GetMapping("grade/student/{id}")
    fun findByStudentId(@PathVariable("id") studentId: Long): List<GradeDto>
}