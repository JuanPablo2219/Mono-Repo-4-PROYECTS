package com.example.Students.service

import com.example.Students.client.GradeFeignClient
import com.example.Students.dto.GradeStudentDto
import com.example.Students.model.Student
import com.example.Students.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.math.exp

@Service
class StudentService {

    @Autowired
    private lateinit var gradeFeignClient: GradeFeignClient

    @Autowired
    lateinit var studentRepository: StudentRepository


    fun list(): List<Student> {
        return studentRepository.findAll()
    }

    fun listById(id: Long?): Student? {
        return studentRepository.findById(id)
    }

    fun findByIdGrades(id: Long?): GradeStudentDto {
        val student = studentRepository.findById(id)?:
        throw ResponseStatusException(HttpStatus.NOT_FOUND)

        val grades = gradeFeignClient.findByStudentId(student.id ?: error("Id del estudiante no debe ser nullo"))

        val studentGrades = GradeStudentDto()
        studentGrades.id = student.id
        studentGrades.full_Name = student.full_Name
        studentGrades.gender = student.gender
        studentGrades.grade = grades

        return studentGrades
    }

    fun save (student: Student): Student {
        try {
            student.full_Name?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("Student name is null")
            student.gender?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("Student gender is null")
            student.email?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("Student email is null")

            return studentRepository.save(student)
        } catch (ex: Exception) {
            throw  ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }

    fun update(student: Student): Student {
        try {
            studentRepository.findById(student.id)
                ?: throw  Exception("Student not found")
            return studentRepository.save(student)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun delete (id: Long?): Boolean? {
        try {
            val response = studentRepository.findById(id)
                ?: throw  Exception("Student id not found")
            studentRepository.deleteById(id!!)
            return true
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }
}