/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primesource;

/**
 *
 * @author Acer1
 */
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.util.Date;
import java.util.List;
/**
 *
 * @author Acer1
 */
@ManagedBean
@RequestScoped
public class ProductsManagedBean {
    
    private int id;
    private String contract;
    private String type;
    private int userId;
    private String userFIO;
    private Date startContartDate;
    
    public ArrayList productsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
    
    public String getContract() {
        return contract;
    }
    public void setContract(String contract) {
        this.contract = contract;
    }
  
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFIO() {
        return userFIO;
    }

    public void setUserFIO(String userFIO) {
        this.userFIO = userFIO;
    }      
    
    public Date getStartContartDate() {
        return startContartDate;
    }
    public void setStartContartDate(Date startContartDate) {
        this.startContartDate = startContartDate;
    }     
 
    @PostConstruct
    public void init() {
        productsList = DatabaseOperation.getProductsListFromDB();
    }

    public ArrayList productsList() {
        return productsList;
    }
    
    public String saveProductDetails(ProductsManagedBean newProductObj) {
        return DatabaseOperation.saveProductsDetailsInDB(newProductObj);
    }   

    public String editProductRecord(ProductsManagedBean productsManagedBean) {
        return DatabaseOperation.editProductsRecordInDB(productsManagedBean);
    }    
    
    public String deleteProductRecord(int productId) {
        return DatabaseOperation.deleteProductsRecordInDB(productId);
    }
    
    public List<String> allUsers() {
        return DatabaseOperation.getAllUsers();
    }
}
