package com.inai.todolist.data.entities;


import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;



import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name ="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "password")
    private String password;
}
