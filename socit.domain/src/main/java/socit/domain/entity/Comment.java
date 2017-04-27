package socit.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Basic(optional = false)
    @Lob
    @Column(name = "text")
    @Getter
    @Setter
    private String text;

    @JoinColumn(name = "id_post", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @Getter
    @Setter
    private Post post;
}
