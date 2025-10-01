package app.security;

import app.config.HibernateConfig;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import static io.javalin.apibuilder.ApiBuilder.*;

public class SecurityRoutes {

    private  EntityManagerFactory emf;
    private SecurityController securityController;
    public SecurityRoutes(EntityManagerFactory emf){
        this.emf = emf;
        this.securityController = new SecurityController(emf);
    }


    public EndpointGroup getSecurityRoutes(){

        return () -> {
            post("/login", securityController.login());
            post("/create", securityController.register());
        };

    }

}
