package foo.bar;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	final Pattern PHONE_PATTERN = Pattern.compile("^\\([0-9]{3}\\)[0-9]{3}-[0-9]{4}$", Pattern.CASE_INSENSITIVE);

	@Autowired
	private UserRepository userRepository;

	public UserController() {
	}

	@PostConstruct
	public void init() {
		this.userRepository.save(new User("Carol", "carol@troll.com", "(666)666-6666"));
	}

	public boolean isNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}

	public boolean isValidEmail(String email) {
		return EMAIL_PATTERN.matcher(email).find();
	}

	public boolean isValidPhone(String phone) {
		return PHONE_PATTERN.matcher(phone).find();
	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	/**
	 * Creates an user
	 */
	@RequestMapping(method = RequestMethod.POST)
	public User create(String name, String email, String phone) {
		if (isNullOrEmpty(name) || isNullOrEmpty(email) || isNullOrEmpty(phone))
			throw new IllegalArgumentException("The values must not be null or empty.");

		if (!isValidEmail(email))
			throw new IllegalArgumentException("The email is not correct.");

		if (!isValidPhone(phone))
			throw new IllegalArgumentException("The phone is not correct.");

		User user = new User(name, email, phone);

		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Email already registred.");

		}

		return user;
	}

	/**
	 * Deletes an user
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public String delete(@PathVariable Long id) {
		userRepository.delete(id);
		return "User succesfully deleted: " + id;
	}

	/**
	 * Returns all users
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public List<User> all() {
		return (List<User>) userRepository.findAll();
	}

	/**
	 * Updates an user
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public User update(@PathVariable Long id, String name, String email, String phone) {
		User user = userRepository.findOne(id);
		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		return userRepository.save(user);
	}

}