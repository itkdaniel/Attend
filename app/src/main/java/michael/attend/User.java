package michael.attend;

import android.util.Log;

import java.util.ArrayList;

public class User {

    public String name, username, email, password;
    public ArrayList<String> groupList;
    public ArrayList<String> hostGroupList;

//    User DataBase "Table":
//    Name, Email, userGroups, hostGroups
//
//    get user.hostGroups
//
//    if groupName is in user.hostGroupList {
//        startActivity(hostViewgroup)
//    }else{
//        startActivity(userVewGroup)
//    }
    private int id;

    public User(String email){
        this.email = email;
        this.groupList = new ArrayList<String>();
    }
    public User(String name, String username, String email, String password, ArrayList<String> groupList){
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.groupList = groupList;
    }

    private void setId(int id){
        this.id = id;
    }

    private int getId(){
        return this.id;
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

    public ArrayList<String> getGroupList(){
        return this.groupList;
    }
    public void setGroupList(ArrayList<String> groupList){
        this.groupList = groupList;
    }
    public void addGroup(String group){
        this.groupList.add(group);
        Log.d("GroupList: ", groupList.toString());
    }

    private void setPassword(String password){
        this.password = password;
    }

    private String getPassword(){
        return this.password;
    }
}
