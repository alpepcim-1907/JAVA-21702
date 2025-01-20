package com.example.projektzaliczenieYusufEpcim.Model;




import lombok.*;



import javax.persistence.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String city;

    private long phone;

    private String department;


    private double salary;


}
