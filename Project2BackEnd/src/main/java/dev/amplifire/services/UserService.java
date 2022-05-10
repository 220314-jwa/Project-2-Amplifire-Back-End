package dev.amplifire.services;

import dev.amplifire.exceptions.AlreadyIssuedException;
import dev.amplifire.exceptions.IncorrectCredentialsException;
import dev.amplifire.exceptions.UsernameAlreadyExistsException;
import dev.amplifire.models.Books;
import dev.amplifire.models.Users;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public Users logIn(String username, String password) throws IncorrectCredentialsException;

    public dev.amplifire.models.Users register(Users newUser) throws UsernameAlreadyExistsException;

    public List<Books> viewAvailableBooks();

    public List<Books> searchBookByTitle(String title);

    public Users checkoutBook(Users user, Books bookToCheckout) throws AlreadyIssuedException, Exception ;

    public Books getBookById(int bookId);

    public Users getUserById(int id);

}
