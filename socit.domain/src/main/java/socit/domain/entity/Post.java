package socit.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@NoArgsConstructor
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "likes")
    @Getter
    @Setter
    private int like;

    @Column(name = "date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date date;

    @Column(name = "status")
    @Getter
    @Setter
    private Boolean status;

    @Column(name = "comment")
    @Getter
    @Setter
    private String comment;

    @Column(name = "all_user")
    @Getter
    @Setter
    private Boolean allUser;

    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @Getter
    @Setter
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    @Getter
    @Setter
    private List<PhotoPost> photoPostList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @Getter
    @Setter
    private List<Bookmark> bookmarkList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @Getter
    @Setter
    private List<Comment> commentList;

    public Post(String comment, boolean status, User user) {
        this.comment = comment;
        this.status = status;
        this.user = user;
    }

}
