package app.security;

import app.config.HibernateConfig;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class SecurityController implements ISecurityController{

    private final EntityManagerFactory emf;
    private SecurityDAO securityDAO;

    public SecurityController(EntityManagerFactory emf){
        this.emf = emf;
        this.securityDAO = new SecurityDAO(emf);
    }


    @Override
    public Handler login() {
    return (Context ctx) -> {
        try {
            User user = ctx.bodyAsClass(User.class);
            User verifiedUser = securityDAO.getVerifiedUser(user.getUsername(), user.getPassword());
            System.out.println("Success med verify af " + verifiedUser.getUsername());
            ctx.json(verifiedUser).status(200);
        } catch (Exception e){
            e.printStackTrace();
        }
    };
    }

    @Override
    public Handler register() {
        return (Context ctx) -> {
           User user = ctx.bodyAsClass(User.class);
           User createdUser = securityDAO.createUser(user.getUsername(), user.getPassword());
           ctx.json(createdUser).status(204);
        } ;
    }

    @Override
    public Handler authenticate() {
        return null;
    }

    @Override
    public boolean authorize(UserDTO userDTO, Set<String> allowedRoles) {
        return false;
    }

    @Override
    public String createToken(UserDTO user) throws Exception {
        return "";
    }

    @Override
    public UserDTO verifyToken(String token) throws Exception {
        return null;
    }
}
