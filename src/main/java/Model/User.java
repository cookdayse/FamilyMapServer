package Model;

import Model.Person;

/**
 * A user.
 */
public class User {
    /**
     * The person this userName is associated with.
     */
    private String personID;
    /**
     * The userName.
     */
    private String userName;
    /**
     * The password.
     */
    private String password;
    /**
     * The email.
     */
    private String email;
    /**
     * The first name.
     */
    private String firstName;
    /**
     * The last name.
     */
    private String lastName;
    /**
     * The gender, either 'm' or 'f'
     */
    private String gender;

    /**
     * creates a user object with all parameters
     *
     * @param personIn person this AssociateduserName is associated with
     * @param user AssociateduserName
     * @param pass password
     * @param emailIn email
     * @param firstNameIn first name
     * @param lastNameIn last name
     * @param genderIn gender, either 'm' or 'f'
     */
    public User(String personIn, String user, String pass, String emailIn, String firstNameIn, String lastNameIn, String genderIn) {
        personID = personIn;
        userName = user;
        password = pass;
        email = emailIn;
        firstName = firstNameIn;
        lastName = lastNameIn;
        gender = genderIn;
    }

    public String getPerson() { return personID; }

    public String getAssociatedUsername() { return userName; }

    public String getPassword() { return password; }

    public String getEmail() { return email; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getGender() { return gender; }

    /**
     * Checks to make sure string in is equal to password
     *
     * @param check string in
     * @return true if equal, false if not
     */
    public boolean checkPassword(String check) {
        if(password.equals(check)) {
            return true;
        }
        return false;
    }

    public boolean equals(Object objectIn) {
        if(objectIn.getClass() != User.class) return false;
        if(!((User) objectIn).getPerson().equals(personID)) return false;
        if(!((User) objectIn).getAssociatedUsername().equals(userName)) return false;
        if(!((User) objectIn).getEmail().equals(email)) return false;
        if(!((User) objectIn).getFirstName().equals(firstName)) return false;
        if(!((User) objectIn).getLastName().equals(lastName)) return false;
        if(!((User) objectIn).getGender().equals(gender)) return false;
        if(!((User) objectIn).checkPassword(password)) return false;

        return true;
    }
}
