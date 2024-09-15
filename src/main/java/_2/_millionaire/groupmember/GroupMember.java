package _2._millionaire.groupmember;

import _2._millionaire.BaseEntity;
import _2._millionaire.group.Groups;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Groups groups;

    @OneToMany(mappedBy = "groupMember")
    private List<Task> tasks;

    @Column(length = 100)
    private String role;

    private Long penalty;
}
