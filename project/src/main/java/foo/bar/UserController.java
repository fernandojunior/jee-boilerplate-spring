package foo.bar;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	public UserController() {
	}

	@PostConstruct
	public void init() {
		this.userRepository.save(new User("Carol", "carol@troll.com", "(666) 666-6666"));
	}

	/**
	 * Creates an user
	 */
	@RequestMapping(method = RequestMethod.POST)
	public User create(String name, String email, String phone) {
		return userRepository.save(new User(name, email, phone));
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