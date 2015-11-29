import java.util.ArrayList;

public class Account {
    private int id;
    private String name;
    private String email;
    ArrayList<Integer> permissions;

    public Account(int id, String name, String email) {
        permissions = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void addPermission(int p){
        permissions.add(p);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
