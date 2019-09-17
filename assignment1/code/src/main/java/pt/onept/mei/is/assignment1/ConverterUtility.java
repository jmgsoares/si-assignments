package pt.onept.mei.is.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ConverterUtility {

	public static void objectToXml() throws JAXBException, FileNotFoundException {

		JAXBContext contextObj = JAXBContext.newInstance(StudentClass.class);

		Marshaller marshallerObj = contextObj.createMarshaller();

		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		List<Student> studentsList = new ArrayList<Student>();

		studentsList.add(new Student(1, "Alberto", 21));
		studentsList.add(new Student(1, "Patricia", 22));
		studentsList.add(new Student(1, "Luis", 21));

		StudentClass studentClass = new StudentClass();

		studentClass.setStudentsList(studentsList);

		marshallerObj.marshal(studentClass, new FileOutputStream("studentClass.xml"));

	}

	public static void xmlToObject() throws JAXBException {
		StudentClass studentClass;

		File file = new File("studentClass.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(StudentClass.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		studentClass = (StudentClass) jaxbUnmarshaller.unmarshal(file);

		for (Student student : studentClass.getStudentsList()) {
			System.out.println(
					"Id: " + student.getId() +
					" Name: " + student.getName() +
					" Age: " + student.getAge()
			);
		}
	}
}
