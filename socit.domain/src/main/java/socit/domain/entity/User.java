package socit.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Log4j
public class User implements UserDetails, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "login")
    @Getter
    @Setter
    private String login;

    @Column(name = "password")
    @Setter
    private String password;

    @Column(name = "status")
    @Getter
    @Setter
    private Boolean status;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Column(name = "firstName")
    @Getter
    @Setter
    private String firstName;

    @Column(name = "lastName")
    @Getter
    @Setter
    private String lastName;

    @Column(name = "authority")
    @Setter
    private String authority;

    @Column(name = "avatar")
    @Setter
    @Getter
    private String avatar;

    @Column(name = "city")
    @Setter
    @Getter
    private String city;

    @Column(name = "date")
    @Setter
    @Getter
    private Calendar date;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private socit.domain.enums.GENDER GENDER;

    @OneToMany(mappedBy = "user")
    @Getter
    @Setter
    private List<URLMassage> urlMassages;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user")
    @Getter
    @Setter
    private List<Friend> friends;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user")
    @Getter
    @Setter
    private List<Friend> friends2;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user")
    @Getter
    @Setter
    private List<Post> posts;

    public User(String login, String password, String firstName, String lastName, String email, Boolean status,
                String authority, String avatar) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.authority = authority;
        this.avatar = avatar;
        log.debug("Set values: firstName = " + firstName + ", lastName = " + lastName + ", email = " + email
                + ", login = " + login + ", password = " + password + ", status = " + status + ", authority = " + authority
                + ", avatar = " + avatar);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new GrantedAuthority[]{(GrantedAuthority) this});
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}