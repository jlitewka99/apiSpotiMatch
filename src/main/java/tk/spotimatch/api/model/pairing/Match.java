package tk.spotimatch.api.model.pairing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import tk.spotimatch.api.model.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User leftUserId;

    @ManyToOne
    private User rightUserId;

    private State state;

    @CreationTimestamp
    private Instant timestamp;

    public enum State {
        NEW, APPROVED, NOT_APPROVED
    }
}
