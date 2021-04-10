package com.wat.pz.costshare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<UserGroup> userGroups;

    @OneToMany(mappedBy = "user")
    private Set<UserExpense> userExpenses;

}
