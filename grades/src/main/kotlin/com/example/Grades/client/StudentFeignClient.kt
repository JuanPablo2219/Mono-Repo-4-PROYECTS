package com.example.Grades.client

import com.example.Grades.dto.StudentDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "STUDENT-MS")
interface StudentFeignClient {
    @GetMapping("/student/{id}")
    fun getStudentsById(@PathVariable("id")id: Long?): List<StudentDto>
}