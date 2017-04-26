package socit.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "url_message")
@NoArgsConstructor
public class URLMassage implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter
  @Setter
  private Integer id;

  @Column(name = "url")
  @Getter
  @Setter
  private String url;

  @JoinColumn(name = "id_user", referencedColumnName = "id")
  @ManyToOne(optional = false)
  @Getter
  @Setter
  private User user;

  public URLMassage(String url, User user){
    this.url = url;
    this.user = user;
  }
}
