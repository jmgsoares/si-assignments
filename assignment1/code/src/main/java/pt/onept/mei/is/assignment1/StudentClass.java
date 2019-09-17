package pt.onept.mei.is.assignment1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name="class", namespace = "urn:dei:ns1")
public class StudentClass {
	List<Student> studentsList;

	public StudentClass() {
		super();
	}

	@XmlElement(name="student")
	public List<Student> getStudentsList() {
		return studentsList;
	}

	public void setStudentsList(List<Student> studentsList) {
		this.studentsList = studentsList;
	}
}
