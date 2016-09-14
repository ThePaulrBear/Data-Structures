public class Employee {
	private String lastName;

	private String firstName;

	private String jobTitle;


	public Employee(String lastName, String firstName, String jobTitle) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.jobTitle = jobTitle;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getJobTitle() {
		return jobTitle;
	}


	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
}