package app.controllers;

import app.config.HibernateConfig;
import app.daos.HotelDAO;
import app.daos.RoomDAO;
import app.dtos.HotelDTO;
import app.dtos.RoomDTO;
import app.entities.Hotel;
import app.entities.Room;
import app.mappers.HotelMapper;
import app.mappers.RoomMapper;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class HotelController implements IC<Context>{

    private EntityManagerFactory emf;
    private HotelDAO hotelDAO;
    private RoomDAO roomDAO;

    public HotelController(EntityManagerFactory emf) {
        this.emf = emf;
         this.hotelDAO = new HotelDAO(emf);
         this.roomDAO = new RoomDAO(emf);
    }
    //Dependency injection så jeg kan teste med getEntityManagerFactoryTest


    @Override
    public void create(Context context) {
        try {
            //Fra requestbody til DTO
            HotelDTO hotelDTO = context.bodyAsClass(HotelDTO.class);
            //DTO til entitet
            Hotel hotel = HotelMapper.toEntity(hotelDTO);
            //Objekt op til db, samt hent nye objekt med dets auto-gen. id
            Hotel hotelDB = hotelDAO.create(hotel);
            //Lav entitet om til DTO og send
            context.status(200).json(HotelMapper.toDTO(hotelDB));
        } catch (Exception e) {
            context.status(500).result(e.getMessage()+"Something went wrong with creating Hotel");
        }
    }

    @Override
    public void update(Context context) {
        //Hent id fra path
        int id = Integer.parseInt(context.pathParam("id"));
        //Fra requestbody til DTO
        HotelDTO hotelDTO = context.bodyAsClass(HotelDTO.class);
        //Fra DTO til entitet
        Hotel hotel = HotelMapper.toEntity(hotelDTO);
        //Sætter id på entitet
        hotel.setId(id);
        //Opdaterer i db
        Hotel hotelDB = hotelDAO.update(hotel);
        if(hotelDB != null){
            context.status(200).json(HotelMapper.toDTO(hotelDB));
        } else {
            context.status(404).result("Hotel Not Found With Id: "+id);
        }
    }

    @Override
    public void delete(Context context) {

        //Henter id fra path
        int id = Integer.parseInt(context.pathParam("id"));

        //deleteHotel er en boolean så sætter den i en if-sætning
        if(hotelDAO.delete(id)){
            context.status(204).result("Hotel Deleted");
        } else {
            context.status(404).result("Hotel Not Found With Id: "+id);
        }

    }

    @Override
    public void getAll(Context context) {

        //Hent alle hotels fra dao
        List<Hotel> hotels = hotelDAO.getAll();
        //Normalt method reference men prøvede lige med lambda
        List<HotelDTO> hotelDTOList = hotels.stream().map(h -> HotelMapper.toDTO(h)).toList();
        context.status(200).json(hotelDTOList);
    }

    @Override
    public void getById(Context context) {
        //Hent id fra path
        int id = Integer.parseInt(context.pathParam("id"));
        Hotel hotel = hotelDAO.getById(id);
        if(hotel != null){
            context.status(200).json(HotelMapper.toDTO(hotel));
        } else {
            context.status(404).result("Hotel Not Found With Id: "+id);
        }
    }

    public void addRoom(Context context) {
        //Hent hotel fra id
        int hotelid = Integer.parseInt(context.pathParam("hotelId"));
        //Henter body fra request
        RoomDTO roomDTO = context.bodyAsClass(RoomDTO.class);
        //DTO til entitet
        Room room = RoomMapper.toEntity(roomDTO);
        //hotel objekt udfra id med dao-metode
       Hotel hotel = hotelDAO.getById(hotelid);
       //Tilføj room
        if(hotel != null && hotelDAO.addRoom(hotel, room)){

            context.status(200).json(HotelMapper.toDTO(hotel));
        } else {
            context.status(404).result("No Hotel Found With ID:  "+hotelid);
        }

    }

    public void removeRoom(Context context) {

        //Hent hotel fra id
        int hotelId = Integer.parseInt(context.pathParam("hotelId"));
        //Hotel objekt udfra id med dao-metode
        Hotel hotel = hotelDAO.getById(hotelId);

        //Hent room fra id
        int roomId = Integer.parseInt(context.pathParam("roomId"));
        //Room objekt hentes via id
        Room room = roomDAO.getById(roomId);

        //Fjern room
        if(hotel != null && hotelDAO.removeRoom(hotel, room)){
            context.status(204);
        } else {
            context.status(404).result("No Hotel Or Room Found With Hotel ID:  "+hotelId+" and Room ID:  "+roomId);
        }

    }

    public void getRoomsForHotel(Context context){
        //Henter id til at få specifik hotel
        int hotelId =  Integer.parseInt(context.pathParam("hotelId"));
        Hotel hotel = hotelDAO.getById(hotelId);
        //Liste af rooms udfra dao-metode
        List<Room> rooms = hotelDAO.getRoomsForHotel(hotel);
        //Gør dem til DTO's via stream()
        List<RoomDTO> roomDTOS = rooms.stream().map(RoomMapper::toDTO).toList();
        context.status(200).json(roomDTOS);
    }



}
