package _2._millionaire.verification_image;

import _2._millionaire.BaseEntity;
import _2._millionaire.verification.Verification;
import jakarta.persistence.*;
import lombok.*;

import java.util.Base64;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VerificationImage extends BaseEntity {

    @Lob
    private byte[] image;

    @ManyToOne()
    @JoinColumn(name = "verification_id", referencedColumnName = "id")
    private Verification verification;

    public String getBase64Image() {
        return Base64.getEncoder().encodeToString(image);
    }
}
