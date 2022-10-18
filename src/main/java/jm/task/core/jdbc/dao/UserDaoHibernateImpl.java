package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(30), lastName VARCHAR(35), age INT);").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table OK");
        } catch (Exception e) {
            System.out.println("Table Error");
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            transaction.begin();
            session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table Del");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Table Not Del");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            User userNew = new User(name, lastName, age);
            session.save(userNew);
            session.getTransaction().commit();
            System.out.println("New User OK");
        } catch (Exception e){
            System.out.println("New User No");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.delete(String.valueOf(id), session.load(User.class, id));
            session.getTransaction().commit();
            System.out.println("User Del");
        } catch (Exception e){
            System.out.println("User No Del");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        List<User> result = new ArrayList<>();
        try {
            session.getTransaction().begin();
            result = session.createQuery("FROM User").list();
            session.getTransaction().commit();
            System.out.println("User Load OK");
        } catch (Exception e) {
            System.out.println("User Load No");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table clean");
        } catch (Exception e) {
            System.out.println("Table Not Clean");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
