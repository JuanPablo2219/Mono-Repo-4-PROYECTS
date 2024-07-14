package com.example.Grades.service

import com.example.Grades.client.StudentFeignClient
import com.example.Grades.model.Grade
import com.example.Grades.repository.GradeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class GradeService {

    @Autowired
    lateinit var gradeRepository: GradeRepository

    @Autowired
    lateinit var studentFeignClient: StudentFeignClient

    fun list(): List<Grade> {
        return gradeRepository.findAll()
    }

    fun listById(id: Long?): Grade? {
        return gradeRepository.findById(id)
    }

    fun save(grade: Grade): Grade {
        try {
            grade.subject?.takeIf { it.trim().isNotEmpty() }
                ?: throw Exception("Grade subject is null or empty")
            grade.grade?.takeIf { it >= 0.0 && it <= 10.0 }
                ?: throw Exception("Grade must be between 0.0 and 10.0")

            return gradeRepository.save(grade)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }

    fun update(grade: Grade): Grade {
        try {
            gradeRepository.findById(grade.id)
                ?: throw Exception("Grade id is null")
            return gradeRepository.save(grade)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.OK, ex.message)
        }
    }

    fun delete(id: Long?): Boolean? {
        try {
            val response = gradeRepository.findById(id)
                ?: throw Exception("Grade with id not found")
            gradeRepository.deleteById(id!!)
            return true
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun findAllByStudentId(studentId: Long): List<Grade> {
        return gradeRepository.findAllByStudentId(studentId)
    }
}