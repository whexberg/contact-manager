package com.thenerdbiker.contactmgr.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Contact {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GenericGenerator(name="increment" , strategy="increment")
    @GeneratedValue(generator="increment")
    private int id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String email;

    @Column
    private long phone;

    // Default constructor for JPA
    public Contact() {}

    public Contact(ContactBuilder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public static class ContactBuilder{
        private String firstname;
        private String lastname;
        private String email;
        private long phone;

        public ContactBuilder(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public ContactBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ContactBuilder withPhone(long phone) {
            this.phone = phone;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }
}
