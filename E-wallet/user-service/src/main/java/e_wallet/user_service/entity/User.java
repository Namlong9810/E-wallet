package e_wallet.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", unique = true)
    private UUID user_id;

    @Column(name = "full_name", length = 25)
    private String full_name;

    @Column(name = "email",  unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Integer phone;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updated_at;
}
