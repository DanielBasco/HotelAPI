package app.dtos;


import lombok.Data;

@Data
public class RoomDTO {

    private Integer id;
    private int hotelId;
    private int number;
    private double price;

    private HotelDTO hotelDTO;

}
