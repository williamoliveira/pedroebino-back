package com.pin2.pedrobino.domain.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pin2.pedrobino.domain.administrator.Administrator;
import com.pin2.pedrobino.domain.client.Client;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "users")
@DiscriminatorValue("USR")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotEmpty
    private String password;

    @Any(
            metaColumn = @Column(name="role_type", length=3),
            fetch = FetchType.LAZY
    )
    @AnyMetaDef(
            idType = "long",
            metaType = "string" ,
            metaValues = {
                    @MetaValue(targetEntity = Administrator.class, value = "ADM" ),
                    @MetaValue(targetEntity = Client.class, value = "CLI" )
            }
    )
    @JoinColumn(name="role_id" )
    private Role role;

    public User() {}

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public User(long id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
