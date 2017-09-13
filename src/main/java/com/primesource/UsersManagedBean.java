/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primesource;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Acer1
 */
@ManagedBean
@RequestScoped
public class UsersManagedBean {

    private int id;
    private int iin;
    private String fullname; 
    private String phone;
    
    public ArrayList usersListFromDB;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIin() {
        return iin;
    }
    
    public void setIin(int iin) {
        this.iin = iin;
    }  
    
    public String getFullname() {
        return fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
        
    @PostConstruct
    public void init() {
        usersListFromDB = DatabaseOperation.getUsersListFromDB();
    }

    public ArrayList usersList() {
        return usersListFromDB;
    } 

    public String saveUserDetails(UsersManagedBean newUserObj) {
        return DatabaseOperation.saveUsersDetailsInDB(newUserObj);
    }   

    public String editUserRecord(int userId) {
        return DatabaseOperation.editUsersRecordInDB(userId);
    }    

    public String updateUserDetails(UsersManagedBean updateUserObj) {
        return DatabaseOperation.updateUsersDetailsInDB(updateUserObj);
    }

    public String deleteUserRecord(int userId) {
        return DatabaseOperation.deleteUsersRecordInDB(userId);
    }
}
