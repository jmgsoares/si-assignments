package pt.onept.mei.is1920.mybay.data.persistence.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.utility.CountryConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PersistenceUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "country", nullable = false)
	@Convert(converter = CountryConverter.class)
	private Country country;

	@OneToMany(mappedBy = "seller")
	private List<PersistenceItem> items;

	public PersistenceUser(User user) {
		this.name = user.getName();
		this.country = user.getCountry();
		this.email = user.getEmail();
		this.password = user.getPassword();
	}

}
