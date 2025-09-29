package app.daos;

import app.entities.Hotel;
import app.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class HotelDAO implements IDAO<Hotel, Integer>{

    private EntityManagerFactory emf;

    public HotelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Hotel> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            //JOIN FETCH gør rooms også bliver hentet med det samme. Distinct gør jeg ikke får dupletter
            return em.createQuery("SELECT DISTINCT h FROM Hotel h LEFT JOIN FETCH h.rooms", Hotel.class).getResultList();
        }
    }

    @Override
    public Hotel getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Hotel.class, id);
        }
    }

    @Override
    public Hotel create(Hotel hotel) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();
            return hotel;
        }
    }

    @Override
    public Hotel update(Hotel hotel) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            // Vi bruger ikke merge men sætter værdierne på det objekt der skal opdateres
           Hotel managedHotel = em.find(Hotel.class, hotel.getId());
           if(managedHotel != null){
               managedHotel.setName(hotel.getName());
               managedHotel.setAddress(hotel.getAddress());

               //Fjerner de muligvis gamle rum fra db
               managedHotel.getRooms().clear();
               //Derefter tilføjer de eksisterende rum
               if(hotel.getRooms() != null){
                   hotel.getRooms().forEach(r -> r.setHotel(managedHotel));
                   managedHotel.getRooms().addAll(hotel.getRooms());
               }

               em.getTransaction().commit();
               return managedHotel;
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
           Hotel hotel = em.find(Hotel.class, id);
            if(hotel != null){
                em.remove(hotel);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        }
    }


    public boolean addRoom(Hotel hotel, Room room) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel managedHotel = em.find(Hotel.class, hotel.getId());
            if(managedHotel != null){
                managedHotel.addRoomToEntity(room);
                //Husk at tilføje room til hibernate/db
                em.persist(room);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }

        }
    }


    public boolean removeRoom(Hotel hotel, Room room) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Hotel managedHotel = em.find(Hotel.class, hotel.getId());
            Room managedRoom = em.find(Room.class, room.getId());

            if(managedHotel != null && managedRoom != null){
                managedHotel.getRooms().remove(managedRoom); //fjerner relation
              managedRoom.setHotel(null);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        }
    }


    public List<Room> getRoomsForHotel(Hotel hotel) {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT r FROM Room r WHERE r.hotel = :hotel", Room.class)
                    .setParameter("hotel", hotel)
                    .getResultList();
        }
    }

}
