package Models;

import java.time.LocalDate;
import java.util.Objects;

public class Books {
	private int bookId;
	private String title, genre, description, status;
	private LocalDate returnDate, issuedDate;
	
	public Books() {
		bookId = 0;
		title = "";
		genre = "";
		description = "";
		status = "";
		returnDate = 0;
		issuedDate = 0;
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
				+ ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, description, genre, status, title);
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
				&& Objects.equals(genre, other.genre) && Objects.equals(status, other.status)
				&& Objects.equals(title, other.title);
	}
	
	
}
