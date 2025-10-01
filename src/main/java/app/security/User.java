package app.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Set;

@Getter
@Setter

@Table(name = "users")
@Entity
public class User {

    @Id
    @Column (name = "username", nullable = true)
    private String username;
    private String password;

    @ManyToMany
    @JoinTable (name = "users_roles",
    joinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(){}

    public User(String username, String password){
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.username = username;
        this.password = hashed;
    }

    public void addRole(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }


    public boolean checkPassword(String candidatePassword){
        if(BCrypt.checkpw(candidatePassword, password)){
            return true;
        } else
            return false;
    }

    public static void main(String[] args) {
        User user1 = new User("Daniel", "AsgerLugterAfPubæ");
        System.out.println(user1.username+ " "+user1.password);
    }


}
