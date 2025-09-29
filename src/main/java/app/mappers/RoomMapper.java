package app.mappers;

import app.dtos.RoomDTO;
import app.entities.Room;

public class RoomMapper {

    public static Room toEntity(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setPrice(roomDTO.getPrice());
        room.setNumber(roomDTO.getNumber());
        room.setHotelId(roomDTO.getHotelId());
        room.setHotel(HotelMapper.toEntity(roomDTO.getHotelDTO()));
        return room;
    }

    public static RoomDTO toDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setPrice(room.getPrice());
        roomDTO.setNumber(room.getNumber());
        roomDTO.setHotelId(room.getHotelId());
        roomDTO.setHotelDTO(HotelMapper.toDTO(room.getHotel()));
        return roomDTO;
    }

}
