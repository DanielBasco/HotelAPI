package app.routes;

import app.config.HibernateConfig;
import app.security.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private EntityManagerFactory emf;
    private HotelRoutes hotelRoutes;
    private RoomRoutes roomRoutes;
    private SecurityRoutes securityRoutes;
    //Ændre det til din egne routes
    public Routes(EntityManagerFactory emf){
        this.hotelRoutes = new HotelRoutes(emf);
        this.roomRoutes = new RoomRoutes(emf);
        this.securityRoutes = new SecurityRoutes(emf);
    }


    public EndpointGroup getRoutes(){
        return () -> {
            get("/", ctx -> ctx.result("Hej"));
            path("/hotels", hotelRoutes.getRoutes());
            path("/rooms", roomRoutes.getRoutes());
            path("/auth", securityRoutes.getSecurityRoutes());
        };
    }

}