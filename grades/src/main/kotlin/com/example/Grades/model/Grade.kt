package com.example.Grades.model

import jakarta.persistence.*


@Table(name = "grade" )
@Entity
class Grade {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(updatable = false)

     var id: Long? = null

    var subject: String? = null

    @Column(name = "grade")
    var grade: Double? = null

    @Column(name = "student_id")
    var studentId: Long? = null

}