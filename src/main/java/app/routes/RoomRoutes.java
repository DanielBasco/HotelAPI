package app.routes;

import app.controllers.RoomController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoutes {


    private RoomController roomController = new RoomController();


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
