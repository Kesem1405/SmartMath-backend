package com.ashcollege.service;

import com.ashcollege.entities.UserEntity;
import com.ashcollege.entities.UserProgressEntity;

import com.ashcollege.utils.Difficulty;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Transactional
@Component
@SuppressWarnings("unchecked")
public class Persist {

    private static final Logger LOGGER = LoggerFactory.getLogger(Persist.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    // Save or update an object
    public void save(Object object) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    // Load an object by ID
    public <T> T loadObject(Class<T> clazz, int oid) {
        return this.getQuerySession().get(clazz, oid);
    }

    // Load a list of objects
    public <T> List<T> loadList(Class<T> clazz) {
        return this.sessionFactory.getCurrentSession().createQuery("FROM " + clazz.getSimpleName()).list();
    }

    // Remove an object
    public void remove(Object o) {
        sessionFactory.getCurrentSession().remove(o);
    }

    // Get the current session for queries
    public Session getQuerySession() {
        return sessionFactory.getCurrentSession();
    }

    // Register a new user
    public void registerUser(String email, String password) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(email);
        newUser.setPassword(password); // Hash the password in a real application
        sessionFactory.getCurrentSession().save(newUser);
    }

    // Login a user
    public UserEntity loginUser(String email, String password) {
        return (UserEntity) sessionFactory.getCurrentSession()
                .createQuery("FROM UserEntity WHERE email = :email AND password = :password")
                .setParameter("email", email)
                .setParameter("password", password) // Compare hashed passwords in a real application
                .uniqueResult();
    }

    // Find user by email
    public UserEntity findUserByEmail(String email) {
        return (UserEntity) sessionFactory.getCurrentSession()
                .createQuery("FROM UserEntity WHERE email = :email")
                .setParameter("email", email)
                .uniqueResult();
    }


    // Email taken
    public boolean isEmailTaken(String email) {
        UserEntity existingUser = (UserEntity) sessionFactory.getCurrentSession()
                .createQuery("FROM UserEntity WHERE email = :email")
                .setParameter("email", email)
                .uniqueResult();
        return existingUser != null;
    }


    public void updateUserDifficulty(UserProgressEntity userProgress, Difficulty newDifficulty) {
        userProgress.setCurrentDifficulty(newDifficulty);
        sessionFactory.getCurrentSession().save(userProgress);  // Save updated progress to the database
    }

}