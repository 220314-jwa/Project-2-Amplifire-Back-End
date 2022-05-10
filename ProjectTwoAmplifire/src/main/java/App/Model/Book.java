package App.Model;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Book {
	// fields
	private int bookId;
	private LocalDate returnDate;
	private LocalDate issuedDate;
	private String title, genre, description, status;
	
	// constructor
	public Book() {
		bookId = 0;
		returnDate = LocalDate.now();
		issuedDate = LocalDate.now();
		title = "";
		genre = "";
		description = "";
		status = "";
	}
	
	// getters and setters
	
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	// Override

	@Override
	public String toString() {
		return "BookCollection [bookId=" + bookId + ", returnDate=" + returnDate + ", issuedDate=" + issuedDate
				+ ", title=" + title + ", genre=" + genre + ", description=" + description + ", status=" + status + "]";
	}
	// Override hashcode & equals

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
		Book other = (Book) obj;
		return bookId == other.bookId && Objects.equals(description, other.description)
				&& Objects.equals(genre, other.genre) && issuedDate == other.issuedDate
				&& returnDate == other.returnDate && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title);
	}
	
	
	
}
