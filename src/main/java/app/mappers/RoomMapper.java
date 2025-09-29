package app.mappers;

import app.dtos.RoomDTO;
import app.entities.Room;

public class RoomMapper {

    public static Room toEntity(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setPrice(roomDTO.getPrice());
        room.setNumber(roomDTO.getNumber());
        return room;
    }

    public static RoomDTO toDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setPrice(room.getPrice());
        roomDTO.setNumber(room.getNumber());
        if( roomDTO.getId() != null ) {
            roomDTO.setHotelId(room.getHotelId());
        }
        return roomDTO;
    }

}
