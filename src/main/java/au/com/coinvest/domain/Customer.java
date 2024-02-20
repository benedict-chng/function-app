package au.com.coinvest.domain;

public class Customer {
    private String name;
    private String email;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name: ")
          .append(name)
          .append(" email:")
          .append(email);
        return sb.toString();
    }
}
