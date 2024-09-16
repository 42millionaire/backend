package _2._millionaire.group;

import _2._millionaire.BaseEntity;
import _2._millionaire.groupjoinrequest.GroupJoinRequest;
import _2._millionaire.groupmember.GroupMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
    private List<GroupJoinRequest> groupJoinRequests;

    @Setter
    @Getter
    @Column(length = 200)
    private String groupName;

    //타입 임시지정함.
    private String groupImage;

    @Setter
    @Getter
    @Column(length = 500)
    private String notice;

}