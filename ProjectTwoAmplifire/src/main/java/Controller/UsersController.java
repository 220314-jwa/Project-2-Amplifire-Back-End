package Controller;

public class UsersController {
	private static UserService userServ = new UserServiceImpl();
	private static Logger log = LogManager.getLogger(PetsController.class);

	// GET to /pets
	public static void getPets(Context ctx) {
		ctx.json(userServ.viewAvailablePets());
	}
	
	// GET to /pets/{id} where {id} is the ID of the pet
	public static void getPetById(Context ctx) {
		log.info("Request to get pet by ID");
		try {
			int id = Integer.parseInt(ctx.pathParam("id"));
			log.debug("getting pet with ID: " + id);
			
			Pet pet = userServ.getPetById(id);
			log.trace("calling userServ.getPetById with argument " + id);
			log.debug("pet retrieved: " + pet);
			if (pet != null) {
				log.trace("writing pet to JSON");
				ctx.json(pet);
			} else {
				log.warn("pet not found");
				ctx.status(HttpCode.NOT_FOUND); // 404 not found
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		}
	}
	
	public static void adoptPet(Context ctx) {
		int petId = Integer.parseInt(ctx.pathParam("id"));
		Pet petToAdopt = userServ.getPetById(petId);
		
		User user = ctx.bodyAsClass(User.class);
		
		try {
			user = userServ.adoptPet(user, petToAdopt);
			
			ctx.json(user);
		} catch (AlreadyAdoptedException e) {
			ctx.status(HttpCode.CONFLICT); // 409 conflict
		} catch (Exception e) {
			ctx.status(HttpCode.BAD_REQUEST); // 400 bad request
		}
	}

}
