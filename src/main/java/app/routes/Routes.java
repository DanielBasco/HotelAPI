package app.routes;

import app.config.HibernateConfig;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    //Ændre det til din egne routes
    private HotelRoutes hotelRoutes = new HotelRoutes();
    private RoomRoutes roomRoutes = new RoomRoutes();


    public EndpointGroup getRoutes(){
        return () -> {
            get("/", ctx -> ctx.result("Hej"));
            path("/hotels", hotelRoutes.getRoutes());
            path("/rooms", roomRoutes.getRoutes());
        };
    }

}