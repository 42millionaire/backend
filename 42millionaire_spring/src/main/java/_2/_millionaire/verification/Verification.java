package _2._millionaire.verification;

import _2._millionaire.BaseEntity;
import _2._millionaire.task.Task;
import _2._millionaire.verification_image.VerificationImage;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Verification extends BaseEntity {

    @Setter
    private String content;

    @OneToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @OneToMany(mappedBy = "verification", cascade = CascadeType.ALL)
    private List<VerificationImage> verificationImages;

}
