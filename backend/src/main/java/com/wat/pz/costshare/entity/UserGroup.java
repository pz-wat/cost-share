package com.wat.pz.costshare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_group")
public class UserGroup {

    @EmbeddedId
    private UserGroupKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "admin")
    private boolean admin;

    public UserGroup(User user, Group group, boolean admin) {
        this.user = user;
        this.group = group;
        this.id = new UserGroupKey(user.getId(), group.getId());
        this.admin = admin;
    }

}
