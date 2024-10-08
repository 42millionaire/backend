package _2._millionaire.group;

import _2._millionaire.BaseEntity;
import _2._millionaire.groupjoin.GroupJoin;
import _2._millionaire.groupmember.GroupMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Groups extends BaseEntity {

    @Getter
    @OneToMany(mappedBy = "groups")
    private List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "groups")
    private List<GroupJoin> groupJoins;

    @Setter
    @Getter
    @Column(length = 200)
    private String groupName;

    //미사용 칼럼
    private String groupImage;

    @Setter
    @Getter
    @Column(length = 500)
    private String notice;

    private Integer monthPenalty;

    private Integer weeklyPenalty;

    private Integer dailyPenalty;

    @PrePersist
    public void setDefaultPenalty() {
        if (this.monthPenalty == null) {
            this.monthPenalty = 0;
        }
        if (this.weeklyPenalty == null) {
            this.weeklyPenalty = 0;
        }
        if (this.dailyPenalty == null) {
            this.dailyPenalty = 0;
        }
    }

    public void setPenalty(Integer monthlyPenalty,
                           Integer weeklyPenalty,
                           Integer dailyPenalty){
        this.monthPenalty = monthlyPenalty;
        this.weeklyPenalty = weeklyPenalty;
        this.dailyPenalty = dailyPenalty;
    }
}