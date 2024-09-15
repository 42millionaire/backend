package _2._millionaire.groupmember;

import _2._millionaire.BaseEntity;
import _2._millionaire.group.Group;
import _2._millionaire.member.Member;
import _2._millionaire.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupMember extends BaseEntity {

    @ManyToOne()
    @JoinColumn()
    private Member member;

    @ManyToOne()
    @JoinColumn()
    private Group group;

    @OneToMany(mappedBy = "groupMember")
    @JoinColumn()
    private List<Task> tasks;

    @Column(length = 100)
    private String role;

    private Long penalty;
}
