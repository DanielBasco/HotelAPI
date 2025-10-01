package app.routes;

import app.config.HibernateConfig;
import app.controllers.RoomController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoutes {

    private EntityManagerFactory emf;
    private RoomController roomController;
    public RoomRoutes(EntityManagerFactory emf){
        this.emf = emf;
        this.roomController = new RoomController(emf);
    }


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
