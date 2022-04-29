package Controller;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import Beans.Book;
import Beans.User;
import Exceptions.AlreadyIssuedException;
import Services.UserService;
import Services.UserServiceImpl;

import io.javalin.http.HttpCode;
import io.javalin.http.Context;

public class BooksController {
	private static UserService userServ = new UserServiceImpl();
	//private static Logger log = LogManager.getLogger(BooksController.class);

	// GET to /pets
		public static void getPets(Context ctx) {
			ctx.json(userServ.viewAvailableBooks());
		}
		
		// GET to /pets/{id} where {id} is the ID of the pet
		public static void getBookById(Context ctx) {
			//log.info("Request to get pet by ID");
			try {
				int id = Integer.parseInt(ctx.pathParam("id"));
				//log.debug("getting pet with ID: " + id);
				
				Book book = userServ.getBookById(id);
				//log.trace("calling userServ.getPetById with argument " + id);
				//log.debug("pet retrieved: " + book);
				if (book != null) {
					//log.trace("writing pet to JSON");
					ctx.json(book);
				} else {
					//log.warn("pet not found");
					ctx.status(HttpCode.NOT_FOUND); // 404 not found
				}
			} catch (NumberFormatException e) {
				//log.error(e.getMessage());
			}
		}
		
		public static void bookToCheckout(Context ctx) {
			int petId = Integer.parseInt(ctx.pathParam("id"));
			Book bookToCheckout = userServ.getBookById(petId);
			
			User user = ctx.bodyAsClass(User.class);
			
			try {
				user = userServ.checkoutBook(user, bookToCheckout);
				
				ctx.json(user);
			} catch (AlreadyIssuedException e) {
				ctx.status(HttpCode.CONFLICT); // 409 conflict
			} catch (Exception e) {
				ctx.status(HttpCode.BAD_REQUEST); // 400 bad request
			}
		}

}
