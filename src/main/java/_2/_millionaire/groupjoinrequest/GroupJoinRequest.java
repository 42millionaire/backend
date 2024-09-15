package _2._millionaire.groupjoinrequest;

import _2._millionaire.BaseEntity;
import _2._millionaire.group.Group;
import _2._millionaire.member.Member;
import jakarta.persistence.Entity;
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

    @ManyToOne()
    @JoinColumn()
    Member member;

    @ManyToOne()
    @JoinColumn()
    Group group;
}
