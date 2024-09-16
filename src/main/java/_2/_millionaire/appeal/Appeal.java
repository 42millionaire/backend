package _2._millionaire.appeal;


import _2._millionaire.BaseEntity;
import _2._millionaire.task.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Appeal extends BaseEntity {

    @Column(length = 100)
    private String content;

    @Column(length = 10)
    @Setter
    private String status;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;
}
