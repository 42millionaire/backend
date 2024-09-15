package _2._millionaire.group;

import _2._millionaire.BaseEntity;
import _2._millionaire.groupjoinrequest.GroupJoinRequest;
import _2._millionaire.groupmember.GroupMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Groups extends BaseEntity {

    @OneToMany(mappedBy = "groups")
    private List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "groups")
    private List<GroupJoinRequest> groupJoinRequests;

    @Column(length = 200)
    private String groupName;

    //타입 임시지정함.
    private String groupImage;

    @Column(length = 500)
    private String notice;

}