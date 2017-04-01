package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {

	public void save(ArrayList<Customer> customers) throws IOException {

		BufferedWriter writerCustomers = new BufferedWriter(new FileWriter("customerinfo.txt"));

		int i = 0;
		// A loop that writes each element of the array line by line to both
		// files.
		while (i < customers.size()) {
			// Write to customerInfo.txt
			writerCustomers.write(customers.get(i).toString());
			writerCustomers.newLine();

			i++;
			
		}
		System.out.println("Files saved.");
		writerCustomers.close();

	}
	
	
	public void saveEmployees(ArrayList<Employee> employees) throws IOException {

		BufferedWriter writerEmployees = new BufferedWriter(new FileWriter("Employeeinfo.txt"));

		int i = 0;
		// A loop that writes each element of the array line by line to both
		// files.
		while (i < employees.size()) {
			// Write to customerInfo.txt
			writerEmployees.write(employees.get(i).toString());
			writerEmployees.newLine();

			i++;
			
		}
		System.out.println("Files saved.");
		writerEmployees.close();

	}

}