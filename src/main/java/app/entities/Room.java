package app.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Room {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private int hotelId;
    private int number;
    private double price;

    @ManyToOne
    private Hotel hotel;
}
