package app.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

@Entity
@Table (name = "roles")
public class Role {

    @Id
    @Column (name = "username", nullable = true)
    private String rolename;
    private String password;

    @ManyToMany (mappedBy = "roles")
    private Set<User> users;

    public Role(){}

    public Role(String rolename){
        this.rolename = rolename;
    }

}
