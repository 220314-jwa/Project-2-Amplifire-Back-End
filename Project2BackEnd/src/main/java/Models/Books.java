package Models;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Books {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column // the column annotation is unnecessary if the name is the same
	private int bookId;
	@Column
	private String title;
	@ManyToOne
	@JoinColumn(name="genre")
	private Genre genre;
	@Column
	private String description;
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;
	@Column
	private LocalDate returnDate;
	@Column
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

	@Override
	public String toString() {
		return "Books [bookId=" + bookId + ", title=" + title + ", genre=" + genre + ", description=" + description
				+ ", status=" + status + ", returnDate=" + returnDate + ", issuedDate=" + issuedDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, description, genre, issuedDate, returnDate, status, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Books other = (Books) obj;
		return bookId == other.bookId && Objects.equals(description, other.description)
				&& Objects.equals(genre, other.genre) && Objects.equals(issuedDate, other.issuedDate)
				&& Objects.equals(returnDate, other.returnDate) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title);
	}

	
	
	
	
}
