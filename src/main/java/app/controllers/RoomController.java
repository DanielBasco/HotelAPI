package app.controllers;

import app.config.HibernateConfig;
import app.daos.RoomDAO;
import app.dtos.RoomDTO;
import app.entities.Room;
import app.mappers.RoomMapper;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RoomController implements IC<Context> {


    private EntityManagerFactory emf;
    private RoomDAO roomDAO;


    //Dependency injection så jeg kan teste med getEntityManagerFactoryTest
        public RoomController(EntityManagerFactory emf) {
            this.emf = emf;
            this.roomDAO = new RoomDAO(emf);
        }

    @Override
    public void create(Context context) {
        try {
            RoomDTO roomDTO = context.bodyAsClass(RoomDTO.class);

            Room room = RoomMapper.toEntity(roomDTO);

            Room roomdb = roomDAO.create(room);

            context.status(200).json(RoomMapper.toDTO(roomdb));
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500).result(e.getMessage()+" Something went wrong with creating a room");
        }
    }

    @Override
    public void update(Context context) {

        int id = Integer.parseInt(context.pathParam("id"));

        RoomDTO roomDTO = context.bodyAsClass(RoomDTO.class);

        Room room =  RoomMapper.toEntity(roomDTO);

        room.setId(roomDTO.getId());

        Room  roomdb = roomDAO.update(room);

        if(roomdb != null) {
            context.status(200).json(RoomMapper.toDTO(roomdb));
        } else {
            context.status(404).result("Room not found with id "+id);
        }
    }

    @Override
    public void delete(Context context) {

        int id = Integer.parseInt(context.pathParam("id"));
        if(roomDAO.delete(id)) {
            context.status(204);
        } else  {
            context.status(404).result("Room not found with id "+id);
        }
    }

    @Override
    public void getAll(Context context) {

        List<Room> rooms = roomDAO.getAll();
        List<RoomDTO> roomDTOS = rooms.stream().map(RoomMapper::toDTO).toList();
        context.status(200).json(roomDTOS);


    }

    @Override
    public void getById(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));
        Room room = roomDAO.getById(id);
        if(room != null) {
            context.status(200).json(RoomMapper.toDTO(room));
        } else {
            context.status(404).result("Room not found with id "+id);
        }
    }
}
