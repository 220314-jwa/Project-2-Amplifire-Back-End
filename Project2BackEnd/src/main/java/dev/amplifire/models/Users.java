package dev.amplifire.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String fullName;
    @OneToMany
    @JoinTable(name="book_renter",
            joinColumns=@JoinColumn(name="renter_id"),
            inverseJoinColumns=@JoinColumn(name="book_id"))
    private List<Books> books;

    public Users() {
        id = 0;
        username = "";
        password = "";
        fullName = "";
        books = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", username=" + username + ", password=" + password + ", fullName=" + fullName
                + ", books=" + books + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(books, fullName, id, password, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Users other = (Users) obj;
        return Objects.equals(books, other.books) && Objects.equals(fullName, other.fullName) && id == other.id
                && Objects.equals(password, other.password) && Objects.equals(username, other.username);
    }


}
