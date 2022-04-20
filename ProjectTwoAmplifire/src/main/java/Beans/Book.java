package Beans;
import java.util.Objects;

public class Book {
	// fields
	private int bookId;
	private String title, description;
	
	// constructor
	public Book() {
		
	}
	
	// getters and setters
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Override
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, description, title);
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
				&& Objects.equals(title, other.title);
	}
	
	
	
}
