package app.mappers;

import app.dtos.HotelDTO;
import app.entities.Hotel;
import app.entities.Room;

import java.util.ArrayList;
import java.util.List;

public class HotelMapper {

    public static Hotel toEntity(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        if( hotelDTO.getId() != null ) {
            hotel.setId(hotelDTO.getId());
        }
        hotel.setName(hotelDTO.getName());
        hotel.setAddress(hotelDTO.getAddress());
        List<Room> rooms = hotelDTO.getRooms().stream()
                        .map(RoomMapper::toEntity)
                        .toList();
        //Relation sættes her begge veje
        rooms.forEach(r -> r.setHotel(hotel));
        hotel.setRooms(rooms);
        return hotel;
    }

    public static HotelDTO toDTO(Hotel hotel) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setId(hotel.getId());
        hotelDTO.setName(hotel.getName());
        hotelDTO.setAddress(hotel.getAddress());
        hotelDTO.setRooms(
                hotel.getRooms().stream()
                        .map(RoomMapper::toDTO)
                        .toList());
        return hotelDTO;
    }

}
