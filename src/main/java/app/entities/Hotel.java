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

    //Eager gør at værdierne i rooms bliver sat med det samme så de ikke er null.
    // orphanRemoval gør at at når det fjernes fra listen sker det også i db når der er transaction commit
    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Room> rooms = new ArrayList<>();


    public void addRoomToEntity(Room room) {
        rooms.add(room);
        // Bi-directional så det også gemmes for begge entiteter
        room.setHotel(this);
    }
}