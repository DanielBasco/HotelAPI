package app.routes;

import app.config.HibernateConfig;
import app.controllers.RoomController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoutes {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    private RoomController roomController = new RoomController(emf);


    public EndpointGroup getRoutes(){
        return () -> {
            get("", roomController::getAll);
            get("/{id}",  roomController::getById);
            post("", roomController::create);
            put("/{id}",  roomController::update);
            delete("/{id}",  roomController::delete);
        };
    }

}
