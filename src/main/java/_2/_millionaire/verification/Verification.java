package _2._millionaire.verification;

import _2._millionaire.BaseEntity;
import _2._millionaire.task.Task;
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
public class Verification extends BaseEntity {

    private String content;

    @OneToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @OneToMany(mappedBy = "verification")
    private List<VerificationImage> verificationImages;

}
