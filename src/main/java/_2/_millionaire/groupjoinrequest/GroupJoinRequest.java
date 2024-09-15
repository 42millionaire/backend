package _2._millionaire.groupjoinrequest;

import _2._millionaire.BaseEntity;
import _2._millionaire.group.Groups;
import _2._millionaire.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupJoinRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Groups groups;
}
