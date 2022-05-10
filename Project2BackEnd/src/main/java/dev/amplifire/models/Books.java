package dev.amplifire.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name= "book_collection")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    @Column // the column annotation is unnecessary if the name is the same
    private String title;
    @ManyToOne
    @JoinColumn(name="genre_id")
    private Genre genre;
    private String description;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern= "MM-dd-yyyy")
    private LocalDate returnDate;
    @JsonFormat (shape= JsonFormat.Shape.STRING, pattern= "MM-dd-yyyy")
    private LocalDate issuedDate;



    public Books() {
        bookId = 0;
        title = "";
        description = "";
        genre = new Genre();
        status = new Status();
        returnDate = LocalDate.now();
        issuedDate = LocalDate.now();
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }
}
