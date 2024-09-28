package _2._millionaire.verification_image;

import _2._millionaire.verification.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationImageRepository extends JpaRepository<VerificationImage, Long> {
    void deleteByVerification(Verification verification);
}
