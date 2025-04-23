package com.beiming.jap.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Teacher
 */
@Entity
@Table(name = "teacher")
@Getter
@Setter
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


//    @ManyToMany(
//            targetEntity = Student.class,
//            fetch = FetchType.EAGER,
//            cascade = CascadeType.DETACH
//    )
//    @JoinTable(
//            name = "teacher_student_relationship",
//            joinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id",foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))},
//            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")}
//    )
//    private List<Student> studentList;

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", studentList=" + studentList +
                '}';
    }
}
