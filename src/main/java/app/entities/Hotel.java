package app.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "hotel")
    @Builder.Default
    @ToString.Exclude
    private List<Room> rooms = new ArrayList<>();


    public void addRoomToEntity(Room room) {
        rooms.add(room);
        // Bi-directional så det også gemmes for begge entiteter
        room.setHotel(this);
    }

    public void removeRoomToEntity(Room room){
        rooms.remove(room);
        // null skal sættes på så man fjerner den nuværende relation mellem rummet og hotellet
        room.setHotel(null);
    }
}