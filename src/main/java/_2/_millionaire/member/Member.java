package _2._millionaire.member;

import _2._millionaire.BaseEntity;
import _2._millionaire.groupjoin.GroupJoin;
import _2._millionaire.groupmember.GroupMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Member extends BaseEntity {

    @OneToMany(mappedBy = "member")
    private List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "member")
    private List<GroupJoin> groupJoins;

    Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Column(length = 30)
    private String name;

    @Column(length = 150)
    private String email;
}
