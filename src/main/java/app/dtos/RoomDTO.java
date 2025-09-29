package app.dtos;


import lombok.Data;

@Data
public class RoomDTO {

    private Integer id;
    private Integer hotelId;
    private int number;
    private double price;

    private HotelDTO hotelDTO;

}
