package pt.onept.mei.is.assignment1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

		marshallerObj.marshal(studentClass, new FileOutputStream("student.xml"));

	}
}