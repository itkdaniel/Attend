public class User {

    private String name, username, email, password;

    public User(String name, String username, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    private void setName(String name){
        this.name = name;
    }

    private String getName(){
        return this.name;
    }

    private void setUsername(String username){
        this.username = username;
    }

    private String getUsername(){
        return this.username;
    }

    private void setEmail(String email){
        this.email = email;
    }

    private String getEmail(){
        return this.email;
    }

    private void setPassword(String password){
        this.password = password;
    }

    private String getPassword(){
        return this.password;
    }
}
