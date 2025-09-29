package app.daos;

import app.entities.Hotel;
import app.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;



public class RoomDAO implements IDAO<Room, Integer> {

    private EntityManagerFactory emf;

    public RoomDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Room> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
        }
    }

    @Override
    public Room getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Room.class, id);
        }
    }

    @Override
    public Room create(Room room) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(room);
            em.getTransaction().commit();
            return room;
        }
    }

    @Override
    public Room update(Room room) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            // Vi bruger ikke merge men sætter værdierne på det objekt der skal opdateres
            Room managedRoom = em.find(Room.class, room.getId());
            if(managedRoom != null){
               room.setHotel(managedRoom.getHotel());
               room.setPrice(managedRoom.getPrice());
               room.setHotelId(managedRoom.getHotelId());
               room.setNumber(room.getNumber());
                em.getTransaction().commit();
                return managedRoom;
            } else {
                em.getTransaction().rollback();
                return null;
            }

        }
    }

    @Override
    public boolean delete(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Room room = em.find(Room.class, id);
            if(room != null){
                em.remove(room);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        }
    }

}
