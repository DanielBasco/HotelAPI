package app.routes;
import app.controllers.HotelController;
import app.controllers.RoomController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HotelRoutes {

    private EntityManagerFactory emf;
    private HotelController hotelController;
    public HotelRoutes(EntityManagerFactory emf){
        this.emf = emf;
        this.hotelController = new HotelController(emf);
    }




    public EndpointGroup getRoutes(){
        return () -> {
            get("", hotelController::getAll);
            get("/{id}", hotelController::getById);
            get("/{hotelId}/rooms", hotelController::getRoomsForHotel);
            post("",  hotelController::create);
            post("/{hotelId}/rooms", hotelController::addRoom);
            put("/{id}", hotelController::update);
            delete("/{id}", hotelController::delete);
            delete("/{hotelId}/rooms/{roomId}", hotelController::removeRoom);

        };
    }
}
