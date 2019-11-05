package pt.onept.mei.is1920.mybay.common.type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pt.onept.mei.is1920.mybay.common.enums.Country;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String name;
	private String email;
	private String password;
	private Country country;
}
