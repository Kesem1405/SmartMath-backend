package com.ashcollege.service;

import com.ashcollege.entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private SessionFactory sessionFactory;

    public UserService() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public boolean registerUser(String email, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Query<UserEntity> query = session.createQuery("FROM UserEntity WHERE email = :email", UserEntity.class);
            query.setParameter("email", email);
            UserEntity existingUser = query.uniqueResult();

            if (existingUser != null) {
                return false; // Email already exists
            }

            UserEntity newUser = new UserEntity();
            newUser.setEmail(email);
            newUser.setPassword(password);
            session.save(newUser);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public UserEntity loginUser(String email, String password) {
        Session session = sessionFactory.openSession();
        try {
            Query<UserEntity> query = session.createQuery("FROM UserEntity WHERE email = :email AND password = :password", UserEntity.class);
            query.setParameter("email", email);
            query.setParameter("password", password); // In a real application, compare hashed passwords
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}