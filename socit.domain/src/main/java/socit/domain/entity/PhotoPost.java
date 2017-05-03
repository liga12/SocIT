package socit.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "photo_post")
@NoArgsConstructor
@Log4j
public class PhotoPost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "location")
    @Getter
    @Setter
    private String location;

    @Column(name = "date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date date;

    @Column(name = "status")
    @Getter
    @Setter
    private Boolean status;

    @JoinColumn(name = "id_post", referencedColumnName = "id")
    @ManyToOne
    @Getter
    @Setter
    private Post post;

    public PhotoPost(String location, boolean status, Post post) {
        this.location = location;
        this.status = status;
        this.post = post;
        log.debug("Set values: location = " + location + ", status" +", Post: like = "+post.getLike()+", date = "+post.getDate()
                +", ststus = "+post.getStatus()+", comment = "+post.getComment()+", allUser = "+post.getAllUser());
    }
}
