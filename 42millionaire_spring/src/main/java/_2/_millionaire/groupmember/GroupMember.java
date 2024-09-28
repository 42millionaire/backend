package _2._millionaire.groupmember;

import _2._millionaire.BaseEntity;
import _2._millionaire.group.Groups;
import _2._millionaire.member.Member;
import _2._millionaire.task.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
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

    @Setter
    @Column(length = 100)
    private String role;

    @Column(columnDefinition = "BIGINT default 0")
    private Long penalty;
}
