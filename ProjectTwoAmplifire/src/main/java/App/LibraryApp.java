package App;

import Controller.BooksController;
import Controller.UsersController;
import Services.UserService;
import Services.UserServiceImpl;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*; 

public class LibraryApp {
	private static UserService userServ = new UserServiceImpl();
	
	public static void main (String[] args) {
		Javalin app = Javalin.create(config -> {
			config.enableCorsForAllOrigins();
		});
		app.start();
		
		app.routes(() -> {
			path("books", () -> {
				get(BooksController::getBooks);
				
				path("{id}", () -> {
					get(BooksController::getBookById);
					// /pets/4/adopt
					put("issued", BooksController::bookToCheckout);
				});
			});
			// all paths starting with /users
			path("users", () -> {
				post(UsersController::registerUser);
				path("{id}", () -> {
					get(UsersController::getUserById);
				});
			});
			// all paths starting with /auth
			path("auth", () -> {
				post(UsersController::logIn);
			});
		});
	}
}
