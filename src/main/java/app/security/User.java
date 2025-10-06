package app.security;

import app.config.HibernateConfig;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@Table(name = "users")
@Entity
public class User {

    @Id
    @Column (name = "username", nullable = false)
    private String username;
    private String password;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name = "users_roles",
    joinColumns = @JoinColumn(name = "username"),
    inverseJoinColumns = @JoinColumn(name = "rolename"))
    private Set<Role> roles = new HashSet<>();

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

        // User user1 = new User("Daniel", "AsgerLugterAfPubæ");
        User test1 = new User("Test1",  "test1");
        User test2 = new User("Test2",  "test2");
        System.out.println(test1.username+ " "+test1.password);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SecurityDAO securityDAO = new SecurityDAO(emf);
        securityDAO.createRole("user");
        securityDAO.createRole("admin");
        securityDAO.createUser(test1.getUsername(), test1.getPassword());
        securityDAO.createUser(test2.getUsername(), test2.getPassword());
        securityDAO.addUserRole(test1.getUsername(), "user");
        securityDAO.addUserRole(test2.getUsername(), "admin");
    }


}
