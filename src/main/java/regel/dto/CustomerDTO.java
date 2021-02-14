package regel.dto;

import regel.models.Address;

public class CustomerDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String sex;

    private Address actualAddress;

    private Address registeredAddress;

    public CustomerDTO(long id, String firstName, String lastName, String middleName, String sex, Address actualAddress, Address registeredAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.sex = sex;
        this.actualAddress = actualAddress;
        this.registeredAddress = registeredAddress;
    }

    public CustomerDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Address getActualAddress() {
        return actualAddress;
    }

    public void setActualAddress(Address actualAddress) {
        this.actualAddress = actualAddress;
    }

    public Address getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(Address registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
