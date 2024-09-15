package _2._millionaire.verification_image;

import _2._millionaire.BaseEntity;
import _2._millionaire.verification.Verification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VerificationImage extends BaseEntity {

    @Lob
    private byte[] image;

    @ManyToOne()
    @JoinColumn(name = "verification_id", referencedColumnName = "id")
    private Verification verification;

}
