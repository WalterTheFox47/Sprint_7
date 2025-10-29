package scooter.model;

public class Courier {
    private String login;
    private String password;
    private String firstName;
    private Integer id;

    public Courier() {
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        final String login = "login" + System.currentTimeMillis();
        final String password = "pass" + System.currentTimeMillis();
        final String firstName = "name" + System.currentTimeMillis();
        return new Courier(login, password, firstName);
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}