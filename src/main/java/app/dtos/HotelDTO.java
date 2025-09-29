package app.dtos;


import lombok.Data;

import java.util.List;

@Data
public class HotelDTO {

    private int id;
    private String name;
    private String address;
    List<RoomDTO> rooms;

}
