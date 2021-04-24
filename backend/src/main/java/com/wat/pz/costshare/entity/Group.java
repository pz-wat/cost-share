package com.wat.pz.costshare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cost_group")
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
    private LocalDate dateCreated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Expense> expenses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<UserGroup> userGroups = new HashSet<>();

    public void addCommonUser(User user) {
        addUser(user, false);
    }

    public void addAdminUser(User user) {
        addUser(user, true);
    }

    private void addUser(User user, boolean isAdmin) {
        UserGroup userGroup = new UserGroup(user, this, isAdmin);
        userGroups.add(userGroup);
        user.getUserGroups().add(userGroup);
    }

    public boolean removeUser(User user) {
        for (Iterator<UserGroup> iterator = userGroups.iterator();
             iterator.hasNext(); ) {
            UserGroup userGroup = iterator.next();

            if (userGroup.getGroup().getId().equals(this.getId()) &&
                    userGroup.getUser().getId().equals(user.getId())) {
                iterator.remove();
                userGroup.getUser().getUserGroups().remove(userGroup);
                userGroup.setUser(null);
                userGroup.setGroup(null);
                return true;
            }
        }
        return false;
    }

}
