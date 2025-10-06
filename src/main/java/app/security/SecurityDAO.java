package app.security;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

public class SecurityDAO implements ISecurityDAO{

    private final EntityManagerFactory emf;

    public SecurityDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public User getVerifiedUser(String username, String password) {
        try(EntityManager em = emf.createEntityManager()){
            User foundUser = em.find(User.class, username);
            if(foundUser != null && foundUser.checkPassword(password)){
                return foundUser;
            } else
                throw new SecurityException("User or password incorrect");
        }
    }

    @Override
    public User addUserRole(String username, String role) {
        try(EntityManager em = emf.createEntityManager()){
            User foundUser = em.find(User.class, username);
            Role foundRole = em.find(Role.class, role);
            if(foundUser != null && foundRole != null){
                em.getTransaction().begin();
                foundUser.addRole(foundRole);
                em.getTransaction().commit();
                return foundUser;
            }
            else
                throw new EntityNotFoundException("User or Role doesnt exist");
        }
    }

    @Override
    public User createUser(String username, String password) {
        try(EntityManager em = emf.createEntityManager()){
            User user = new User(username, password);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }

    @Override
    public Role createRole(String role) {
        try(EntityManager em = emf.createEntityManager()){
            Role newRole = new Role(role);
            em.getTransaction().begin();
            em.persist(newRole);
            em.getTransaction().commit();
            return newRole;
            }

        }
    }

