package socit.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bookmark")
@NoArgsConstructor
public class Bookmark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @JoinColumn(name = "id_post", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @Getter
    @Setter
    private Post post;
}
