package foo.bar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User extends Model {

	@NotNull
	@Column(unique = true)
	private String email;

	@NotNull
	private String name;

	@NotNull
	private String phone;

	public User() {
	}

	public User(String name, String email, String phone) {
		this.email = email;
		this.name = name;
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}