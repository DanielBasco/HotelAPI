package app.dtos;


import lombok.Data;

import java.util.List;

@Data
public class HotelDTO {

    private Integer id;
    private String name;
    private String address;
    private List<RoomDTO> rooms;

}
