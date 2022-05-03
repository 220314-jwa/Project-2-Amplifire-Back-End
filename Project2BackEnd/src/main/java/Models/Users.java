package Models;

import java.util.Objects;

public class Users {
	private int id;
	private String username, password, fullName;
	
	public Users() {
		id = 0;
		username = "";
		password = "";
		fullName = "";
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

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", fullName=" + fullName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullName, id, password, username);
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
		return Objects.equals(fullName, other.fullName) && id == other.id && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	
}
