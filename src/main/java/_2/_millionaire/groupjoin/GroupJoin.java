package _2._millionaire.groupjoin;

import _2._millionaire.BaseEntity;
import _2._millionaire.group.Groups;
import _2._millionaire.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupJoin extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @JoinColumn()
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    @Getter
    private Groups groups;
}
