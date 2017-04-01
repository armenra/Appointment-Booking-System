package main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Owner extends Member {

	private static ArrayList<Employee> employeeArray = new ArrayList<Employee>();

	private String businessName;
	Scanner input = new Scanner(System.in);

	public Owner() {
		super(null, null, null, null, null, null);
	}

	public Owner(String username, String password, String firstName, String lastName, String address,
			String contactNumber, String businessName) {
		super(username, password, firstName, lastName, address, contactNumber);
		this.businessName = businessName;
	}

	public String getBusinessName() {
		return businessName;
	}
	
	

	public Boolean createEmployee(){
		
		System.out.println("                     Adding New Employee");
		System.out.println("**********************************************************");
		System.out.println("Enter 'q','b','quit' to exit at anytime ");
		System.out.println("");
		
		Employee newEmployee;
		
		newEmployee = getEmployeeInfo();
		if(newEmployee == null){
			System.out.println("Adding new emplyee failed");
			return false;
		}
		addEmployee(newEmployee);
			//getEmployeeArray().add(newEmployee);
		System.out.println("Employee " + newEmployee.getFirstName() + " " + newEmployee.getLastName() + " added.");
		
		return true;		
	}
	
	public Boolean addEmployee(Employee employee){
		if(employee == null){
			return false;
		}
		getEmployeeArray().add(employee);
		return true;
	}

	public Employee getEmployeeInfo() {
		// Add error checking make sure id is unique

		Utility util = new Utility();
		String id = util.createID();
		System.out.println("Employee First Name: ");
		String firstName = input.nextLine();
		if (util.quitFunction(firstName)) {
			return null;
		}
		while (util.validateName(firstName) == false) {
			firstName = input.nextLine();
			if (util.quitFunction(firstName)) {
				return null;
			}
		}

		System.out.println("Employee Last Name: ");
		String lastName = input.nextLine();
		if (util.quitFunction(lastName)) {
			return null;
		}
		while (util.validateName(lastName) == false) {
			lastName = input.nextLine();
			if (util.quitFunction(lastName)) {
				return null;
			}
		}

		Employee employee = makeEmployeeObj(firstName, lastName, id);
		return employee;
	}

	public Employee makeEmployeeObj(String firstName,String lastName, String id){
		Utility util = new Utility();
		if( (util.checkString(firstName)==false) || (util.checkString(lastName)==false) || (util.checkString(id) == false) ){
			return null;
		}
		else{
		Employee newEmployee = new Employee(firstName, lastName, id, null, null);
		return newEmployee;
		}
	}
	
	public Boolean deleteEmployee(){
			
			Utility util = new Utility();
			Scanner keyboard = new Scanner(System.in);
			String employeeId;
			String sure;
			Owner owner = new Owner();
			System.out.println("               Delete Employee");
			System.out.println("************************************************");
			
			int j = 0;
			while(j < owner.getEmployeeArray().size()){
				System.out.print(owner.getEmployeeArray().get(j).getId() + " | ");
				System.out.print(owner.getEmployeeArray().get(j).getFirstName() + " ");
				System.out.println(owner.getEmployeeArray().get(j).getLastName() + " ");
				j++;
			}
			System.out.println("");
			System.out.println("Select an employee, input Id: ");
			do {
				employeeId = keyboard.nextLine();
				if (util.quitFunction(employeeId)) {
					return false;
				}
			} while (util.validateEmployeeId(employeeId) == false);
			
			
			for(int i = 0; i < owner.getEmployeeArray().size(); i++){
				if (owner.getEmployeeArray().get(i).getId().equals(employeeId)) {
					System.out.println("are you sure you want to delete " + getEmployeeArray().get(i).getId() + " Y - N" );
					sure = keyboard.nextLine();
					if((sure.equals("Y")) || (sure.equals("y"))){
						System.out.println("Employee " + owner.getEmployeeArray().get(i).getFirstName() + " " + owner.getEmployeeArray().get(i).getLastName() + " deleted successfully");
						owner.getEmployeeArray().remove(i);
					}
					else{
						return false;
					}
				}
			}
			return true;
		}

	public void addWorkingTimes() {
		Owner owner = new Owner();
		Utility util = new Utility();
		Scanner keyboard = new Scanner(System.in);
		String employeeId, newDate, newTime;
		DateTimeFormatter timeFormatter;
		LocalDate date;
		LocalTime startTime, endTime;
		LocalDateTime startDateTime, endDateTime;
		boolean validEntry;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");

		System.out.println("Add employee work time");
		System.out.println("*************************");
		System.out.println("Employees");
		System.out.println("=====================");
		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			System.out.println("Name: " + owner.getEmployeeArray().get(i).getFirstName() + " "
					+ owner.getEmployeeArray().get(i).getLastName());
			System.out.println("Id:   " + owner.getEmployeeArray().get(i).getId());
			System.out.println("---------------------");
		}
		System.out.println("Select an employee, input Id: ");
		do {
			employeeId = keyboard.nextLine();
			if (util.quitFunction(employeeId)) {
				return;
			}
		} while (util.validateEmployeeId(employeeId) == false);

		System.out.println("Type in new work date (day/month/year)");
		do {
			newDate = keyboard.nextLine();
			if (util.quitFunction(newDate)) {
				return;
			}
		} while (util.validateDate(newDate) == false);
		date = LocalDate.parse(newDate, dateFormat);

		System.out.println("Type in start time (hours:minutes)");
		do {
			newTime = keyboard.nextLine();
			if (util.quitFunction(newTime)) {
				return;
			}
		} while (util.validateTime(newTime) == false);
		timeFormatter = assignTimeFormat(newTime);
		startTime = LocalTime.parse(newTime, timeFormatter);

		System.out.println("Type in end time (hours:minutes)");
		do {
			newTime = keyboard.nextLine();
			if (util.quitFunction(newTime)) {
				return;
			}
		} while (util.validateTime(newTime) == false);
		timeFormatter = assignTimeFormat(newTime);
		endTime = LocalTime.parse(newTime, timeFormatter);

		startDateTime = LocalDateTime.of(date, startTime);
		endDateTime = LocalDateTime.of(date, endTime);

		validEntry = validateNewWorkTime(employeeId, startDateTime, endDateTime);
		if (validEntry == true) {
			System.out.println("New work hours added for " + employeeId);
		} else {
			System.out.println("New work hours were not added");
		}
		return;
	}

	public DateTimeFormatter assignTimeFormat(String time) {
		LocalTime currentTime;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
		DateTimeFormatter timeFormat2 = DateTimeFormatter.ofPattern("h:mma");

		try {
			currentTime = LocalTime.parse(time, timeFormat);
			return timeFormat;
		} catch (Exception e) {}

		try {
			currentTime = LocalTime.parse(time, timeFormat2);
			return timeFormat2;
		} catch (Exception e) {}

		return null;

	}

	public boolean validateNewWorkTime(String employeeId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		Owner owner = new Owner();
		Business business = new Business();
		LocalDateTime now = LocalDateTime.now();

		for (int i = 0; i < owner.getEmployeeArray().size(); i++) {
			if (owner.getEmployeeArray().get(i).getId().equals(employeeId)) {
				for (int j = 0; j < owner.getEmployeeArray().get(i).getStartTimes().size(); j++) {
					if (owner.getEmployeeArray().get(i).getStartTimes().get(j).toLocalDate()
							.equals(startDateTime.toLocalDate())) {
						System.out.println("employee already working on this date");
						return false;
					}
				}
				if (validateDayOfWeek(startDateTime) == false) {
					return false;
				}

				if (startDateTime.compareTo(endDateTime) > 0) {
					System.out.println("end time is before start time");
					return false;
				} else if (startDateTime.toLocalTime().isBefore(business.getOpeningTime())) {
					System.out.println("cannot start before business is open");
					return false;
				} else if (endDateTime.toLocalTime().isAfter(business.getClosingTime())) {
					System.out.println("cannot have end after business is closed");
					return false;
				} else if (startDateTime.toLocalDate().isBefore(now.toLocalDate())) {
					System.out.println("cannot make work time in the past");
					return false;
				} else if (startDateTime.toLocalDate().isAfter(now.plusMonths(1).toLocalDate())) {
					System.out.println("cannot assign work time beyond one month");
					return false;
				} else if(endDateTime.getHour()-startDateTime.getHour()<3){
					System.out.println("cannot work for less than 3 hours");
					return false;
				} else if(!endDateTime.toLocalDate().equals(startDateTime.toLocalDate())){
					System.out.println("must work on the same day");
					return false;
				}

				owner.getEmployeeArray().get(i).getStartTimes().add(startDateTime);
				owner.getEmployeeArray().get(i).getEndTimes().add(endDateTime);
				System.out.println("new work time added");
				return true;
			}
		}
		return false;
	}

	public void showAllWorkerAvailability() {
		Owner owner = new Owner();
		Utility util = new Utility();
		Scanner keyboard = new Scanner(System.in);
		String employeeId, newDate, newTime;
		boolean validEntry;
		ArrayList<Employee> employees = owner.getEmployeeArray();
		LocalDateTime now = LocalDateTime.now();
		System.out.println("View employee availability");
		System.out.println("*************************");

		int i = 0;
		while (i < employees.size()) {
			System.out.println(getEmployeeArray().get(i).getId() + "'s availability:");
			if (owner.getEmployeeArray().get(i).getStartTimes().isEmpty()) {
				System.out.println(owner.getEmployeeArray().get(i).getId() + " has no work hours");
			} else {
				ArrayList<LocalDateTime> startTimes = owner.getEmployeeArray().get(i).getStartTimes();
				ArrayList<LocalDateTime> endTimes = owner.getEmployeeArray().get(i).getEndTimes();
				int k = 0;
				while (k < owner.getEmployeeArray().get(i).getStartTimes().size()) {
					int week = 1;
					while (week <= 7) {
						if (now.plusDays(week).toString().substring(0, 10)
								.equals(owner.getEmployeeArray().get(i).getStartTimes().toString().substring(1, 11))) {
							outputWorkHours(startTimes.get(i).toString() + endTimes.get(k).toString());
						}
						week++;
					}
					k++;
				}
				System.out.println("*************************");
			}
			i++;
		}
	}
	public  void outputWorkHours(String workHours) {
		String endTime = (workHours.substring(27,32));
		String date = workHours.substring(0,10);
		String startTime = workHours.substring(11,16);
		System.out.println("Date: " + date + " from " +startTime + " to " + endTime);

	}
	public void viewBookingSummary() {
		Main main = new Main();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Business business = new Business();
		String formattedTime, formattedTimePlusDuration, dateAndDay;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		int counter = main.getAppointmentArray().size();
		LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
		Boolean open = null;

		if (main.getAppointmentArray().isEmpty()) {
			System.out.println("No appointments made yet");
			return;
		}
		currentTime = owner.setEarliestDate(currentTime);
		System.out.println("Booking Summary");
		while (counter > 0) {
			currentTime = currentTime.withHour(business.getOpeningTime().getHour());
			currentTime = currentTime.withMinute(business.getOpeningTime().getMinute());
			open = owner.validateDayOfWeek(currentTime);
			if (open == false) {
				currentTime = currentTime.plusHours(24 - currentTime.getHour());
				continue;
			} else if (open == true) {
				dateAndDay = currentTime.format(dateAndDayFormat);
				System.out.println("==================================================");
				System.out.println(dateAndDay);
				System.out.println("==================================================");
			}
			while (currentTime.toLocalTime().compareTo(business.getClosingTime()) == -1 || currentTime.getHour() == 0) {
				for (int i = 0; i < main.getAppointmentArray().size(); i++) {
					if (main.getAppointmentArray().get(i).getDateAndTime().equals(currentTime)) {
						formattedTime = currentTime.format(timeFormat);
						formattedTimePlusDuration = currentTime.plusMinutes(appointment.getAppointmentDuration())
								.format(timeFormat);
						System.out.println("--------------------------------------------------");
						System.out.println(formattedTime + "-" + formattedTimePlusDuration);
						owner.printAppointmentDetails(i);
						System.out.println("--------------------------------------------------");
						counter--;
						currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
						i = 0;
					}
				}
				currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
			}
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		return;
	}

	// Test
	public LocalDateTime setEarliestDate(LocalDateTime currentTime) {
		Main main = new Main();
		for (int i = 0; i < main.getAppointmentArray().size(); i++) {
			if (main.getAppointmentArray().get(i).getDateAndTime().compareTo(currentTime) < 0) {
				currentTime = main.getAppointmentArray().get(i).getDateAndTime();
			}
		}
		return currentTime;
	}

	// Test
	public boolean validateDayOfWeek(LocalDateTime currentTime) {
		Business business = new Business();
		for (int i = 0; i < business.getOpeningDays().length; i++) {
			if (business.getOpeningDays()[i].equals(currentTime.getDayOfWeek())) {
				return true;
			}
		}
		return false;
	}

	public void viewUpcomingBookings() {
		Main main = new Main();
		Owner owner = new Owner();
		Appointment appointment = new Appointment();
		Business business = new Business();

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mma");
		DateTimeFormatter dateAndDayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy E");
		String formattedTime, formattedTimePlusDuration, dateAndDay;
		LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		int counter = 0;

		if (main.getAppointmentArray().isEmpty()) {
			System.out.println("No appointments made yet");
			return;
		}
		if (currentTime.toLocalTime().compareTo(business.getClosingTime()) > 0) {
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		System.out.println("Bookings over the next week");
		while (currentTime.compareTo(now.plusDays(7)) < 0) {
			currentTime = currentTime.withHour(business.getOpeningTime().getHour());
			currentTime = currentTime.withMinute(business.getOpeningTime().getMinute());
			currentTime = owner.adjustTimePresentDay(currentTime);
			dateAndDay = currentTime.format(dateAndDayFormat);
			System.out.println("==================================================");
			System.out.println(dateAndDay);
			System.out.println("==================================================");

			while (!(currentTime.toLocalTime().compareTo(business.getClosingTime()) == 0)) {
				if (currentTime.compareTo(main.getAppointmentArray().get(counter).getDateAndTime()) == 0) {
					formattedTime = main.getAppointmentArray().get(counter).getDateAndTime().format(timeFormat);
					formattedTimePlusDuration = main.getAppointmentArray().get(counter).getDateAndTime()
							.plusMinutes(appointment.getAppointmentDuration()).format(timeFormat);
					System.out.println("--------------------------------------------------");
					System.out.println(formattedTime + "-" + formattedTimePlusDuration);
					owner.printAppointmentDetails(counter);
					System.out.println("--------------------------------------------------");
				}
				if (counter == main.getAppointmentArray().size() - 1) {
					currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
					counter = 0;
				} else {
					counter++;
				}
			}
			currentTime = currentTime.plusHours(24 - currentTime.getHour());
		}
		return;
	}

	public void printAppointmentDetails(int counter) {
		Main main = new Main();
		Owner owner = new Owner();

		for (int j = 0; j < main.getCustomerArray().size(); j++) {
			if (main.getCustomerArray().get(j).getUsername()
					.equals(main.getAppointmentArray().get(counter).getCustomerUsername())) {
				System.out.println("Customer Name: " + main.getCustomerArray().get(j).getFirstName() + " "
						+ main.getCustomerArray().get(j).getLastname());
				System.out.println("Address: " + main.getCustomerArray().get(j).getAddress());
				System.out.println("Contact Number: " + main.getCustomerArray().get(j).getContactNumber());
			}
		}
		for (int k = 0; k < owner.getEmployeeArray().size(); k++) {
			if (owner.getEmployeeArray().get(k).getId()
					.equals(main.getAppointmentArray().get(counter).getEmployeeId())) {
				System.out.println("Employee Name: " + owner.getEmployeeArray().get(k).getFirstName() + " "
						+ owner.getEmployeeArray().get(k).getLastName());
				System.out.println("Employee ID: " + owner.getEmployeeArray().get(k).getId());
			}
		}
	}

	public LocalDateTime adjustTimePresentDay(LocalDateTime currentTime) {
		Appointment appointment = new Appointment();
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		while (now.getDayOfWeek().equals(currentTime.getDayOfWeek()) && now.getHour() >= currentTime.getHour()
				&& now.getDayOfYear() == currentTime.getDayOfYear()) {
			if (now.getHour() >= currentTime.getHour()) {
				currentTime = currentTime.plusMinutes(appointment.getAppointmentDuration());
			}
		}
		return currentTime;
	}

	public Boolean login(String username, String password) {
		Main main = new Main();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Owner> ownerArray = main.getOwnerArray();

		int index = 0;
		while (index < ownerArray.size()) {
			MembersSearch.add(ownerArray.get(index).getUsername() + ownerArray.get(index).getPassword());
			index++;
		}
		if (MembersSearch.contains(username + password)) {
			System.out.println("Login Successful (Owner)");

			int selection;
			String select;
			do {
				main.createOwnerMenu();
				selection = 0;
				select = null;
				try {
					select = input.nextLine();
					selection = Integer.parseInt(select);
				} catch (Exception e) {

				}
				switch (selection) {
				case 1: {
					createEmployee();
					break;
				}
				
				case 2: {
					deleteEmployee();
					break;
				}
				case 3: {
					addWorkingTimes();
					break;
				}
				case 4: {
					showAllWorkerAvailability();
					break;
				}
				case 5: {
					viewBookingSummary();
					break;
				}
				case 6: {
					viewUpcomingBookings();
					break;
				}
				case 7: {
					return true;
				}
				default: {
					System.out.println("Invalid Input, please try again:");
					break;
				}
				}

			} while (selection != 7);
		} else {
			return false;
		}
		return true;
	}

	public ArrayList<Employee> getEmployeeArray() {
		return employeeArray;
	}

}