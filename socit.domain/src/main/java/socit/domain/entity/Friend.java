package socit.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import socit.domain.enums.FRIENDSTATUS;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friend")
@NoArgsConstructor
@Log4j
public class Friend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @Getter
    @Setter
    private User user;

    @JoinColumn(name = "id_friend", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @Getter
    @Setter
    private User friend;

    @Column(name = "status")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private FRIENDSTATUS friendstatus;

    public Friend(User user, User friend, FRIENDSTATUS friendstatus) {
        this.user = user;
        this.friend = friend;
        this.friendstatus = friendstatus;
    }
}
