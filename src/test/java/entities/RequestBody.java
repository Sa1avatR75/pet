package entities;

public class RequestBody {
    private String email;
    private String password;

    public RequestBody(String login, String password) {
        this.email = login;
        this.password = password;
    }

    public RequestBody() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String login) {
        this.email = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
