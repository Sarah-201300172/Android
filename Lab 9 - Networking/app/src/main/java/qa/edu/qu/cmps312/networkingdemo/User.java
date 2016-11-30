package qa.edu.qu.cmps312.networkingdemo;

/**
 * Created by abdulahi on 11/26/16.
 */
public class User {

    private String title;
    private String firstName;
    private String lastName;
    private String picture_url;

    public User() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    @Override
    public String toString() {
        return "User{" +
                "title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture_url='" + picture_url + '\'' +
                '}';
    }
}
