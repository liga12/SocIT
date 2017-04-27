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

//    @OneToMany(fetch = FetchType.EAGER,  cascade = {CascadeType.MERGE, CascadeType.PERSIST},
//             mappedBy = "post")
////    @Fetch(value = FetchMode.SUBSELECT)
//    @Getter
//    @Setter
//    private List<PhotoPost> photoPostList;
//
//    @OneToMany(fetch = FetchType.LAZY,  cascade = {CascadeType.PERSIST, CascadeType.MERGE},
//             mappedBy = "post")
//    @Getter
//    @Setter
//    private List<Bookmark> bookmarkList;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, }
//           , mappedBy = "post")
//    @Getter
//    @Setter
//    private List<Comment> commentList;

    public Post(String comment, boolean status, User user) {
        this.comment = comment;
        this.status = status;
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
