package com.thenerdbiker.contactmgr;

import com.thenerdbiker.contactmgr.model.Contact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class Application {
    // Hold a reusable reference to a SessionFactory (since we need only one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Create a StandardServiceRegistry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new Contact.ContactBuilder("William", "Hexberg")
                .withEmail("whexberg@gmail.com")
                .withPhone(5306320477L)
                .build();
        int id = save(contact);

        // Display a list of contacts before the update
        System.out.printf("%nBefore Update%n");
        fetchAllContacts().stream().forEach(System.out::println);

        // Get the persisted contact
        Contact c = findContactById(id);

        // Update the contact
        c.setFirstname("Beatrix");

        // Persist the changes
        System.out.printf("%nUpdating...%n");
        update(c);
        System.out.printf("%nUpdating Complete%n");

        // Display a list of contacts after the update
        System.out.printf("%nAfter Update%n");
        fetchAllContacts().stream().forEach(System.out::println);

        // Display a list of contacts after the update
        System.out.printf("%nDELETING!%n");
        fetchAllContacts().stream().forEach(Application::delete);
        System.out.printf("%nALL RECORDS DELETED!%n");
        fetchAllContacts().stream().forEach(System.out::println);

    }

    private static Contact findContactById(int id) {
        Session session = sessionFactory.openSession();

        Contact contact = session.get(Contact.class, id);

        session.close();

        return contact;
    }

    private static void delete(Contact contact) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(contact);

        session.getTransaction().commit();

        session.close();
    }

    private static void update(Contact contact) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.update(contact);

        session.getTransaction().commit();

        session.close();
    }

    private static List<Contact> fetchAllContacts() {
        // Open a session
        Session session = sessionFactory.openSession();

        // DEPRECATED: Create Criteria
        // Criteria criteria = session.createCriteria(Contact.class);

        // DEPRECATED: Get a list of Contact objects according to the Criteria object
        // List<Contact> contacts = criteria.list();

        // UPDATED: Create CriteriaBuilder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // UPDATED: Create CriteriaQuery
        CriteriaQuery<Contact> criteria = builder.createQuery(Contact.class);

        // UPDATED: Specify criteria root
        criteria.from(Contact.class);

        // UPDATED: Execute query
        List<Contact> contacts = session.createQuery(criteria).getResultList();

        // Close the session
        session.close();

        return contacts;
    }

    private static int save(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // User the session to save the contact
        int id = (int)session.save(contact);

        //Commit the transaction
        session.getTransaction().commit();

        //Close the session
        session.close();

        return id;
    }
}
