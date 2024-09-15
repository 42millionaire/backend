package _2._millionaire.task;

import _2._millionaire.BaseEntity;
import _2._millionaire.appeal.Appeal;
import _2._millionaire.groupmember.GroupMember;
import _2._millionaire.verification.Verification;
import _2._millionaire.verification_image.VerificationImage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Task extends BaseEntity {

    @Column(length = 10)
    private String type;

    @Column(length = 300)
    private String content;

    @Column(length = 20)
    private String status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Appeal> appeals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupmember_id")
    private GroupMember groupMember;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    private Verification verification;
}
