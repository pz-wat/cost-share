package com.wat.pz.costshare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "group")
public class Group {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Expense> expenses;

    @OneToMany(mappedBy = "group")
    private Set<UserGroup> userGroups;

}
