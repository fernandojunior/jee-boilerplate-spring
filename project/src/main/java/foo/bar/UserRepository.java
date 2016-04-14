package foo.bar;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Finds an User by its email.
	 */
	public User findByEmail(String email);

}