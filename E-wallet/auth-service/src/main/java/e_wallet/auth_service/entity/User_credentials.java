package e_wallet.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;



import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "User_credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID user_id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", unique = true)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true)
    private Instant updated_at;
}
