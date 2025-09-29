package app.mappers;

import app.dtos.HotelDTO;
import app.entities.Hotel;

public class HotelMapper {

    public static Hotel toEntity(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelDTO.getId());
        hotel.setName(hotelDTO.getName());
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setRooms(
                hotelDTO.getRooms().stream()
                        .map(RoomMapper::toEntity)
                        .toList());
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
