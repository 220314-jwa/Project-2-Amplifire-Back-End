package App.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
	private int userId;
	private String username, password, fullName;
	private List<Book> books;
	
	public User() {
		userId = 0;
		username = "";
		password = "";
		fullName = "";
		books = new ArrayList<>();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", fullName=" + fullName
				+ ", books=" + books + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(books, fullName, password, userId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(books, other.books) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(password, other.password) && userId == other.userId
				&& Objects.equals(username, other.username);
	}

	

	
	
	
}
