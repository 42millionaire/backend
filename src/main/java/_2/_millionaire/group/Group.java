package _2._millionaire.group;

import _2._millionaire.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Group extends BaseEntity {

    @Column(length = 200)
    String groupName;

    //타입 임시지정함.
    String groupImage;

    @Column(length = 500)
    String notice;

}