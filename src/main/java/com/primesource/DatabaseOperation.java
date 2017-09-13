/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primesource;

/**
 *
 * @author Acer1
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

public class DatabaseOperation {

	public static Statement stmtObj;
	public static Connection connObj;
	public static ResultSet resultSetObj;
	public static PreparedStatement pstmt;

	/* Method To Establish Database Connection */
	public static Connection getConnection(){  
		try{  
			Class.forName("com.mysql.jdbc.Driver");     
			String db_url ="jdbc:mysql://localhost:3306/primesource",
					db_userName = "root",
					db_password = "barcelona";
			connObj = DriverManager.getConnection(db_url,db_userName,db_password);  
		} catch(Exception sqlException) {  
			sqlException.printStackTrace();
		}  
		return connObj;
	}

        // -- Клиенты
	/* Method To Fetch The Student Records From Database */
	public static ArrayList<UsersManagedBean> getUsersListFromDB() {
		ArrayList<UsersManagedBean> usersList = new ArrayList<UsersManagedBean>();  
		try {
			stmtObj = getConnection().createStatement();    
			resultSetObj = stmtObj.executeQuery("select * from users");    
			while(resultSetObj.next()) {  
				UsersManagedBean stuObj = new UsersManagedBean(); 
				stuObj.setId(resultSetObj.getInt("ID"));  
				stuObj.setIin(resultSetObj.getInt("IIN"));  
				stuObj.setFullname(resultSetObj.getString("FULLNAME"));  
				stuObj.setPhone(resultSetObj.getString("PHONE"));  
				usersList.add(stuObj);  
			}   
			System.out.println("Total Records Fetched: " + usersList.size());
			connObj.close();
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} 
		return usersList;
	}

	/* Method Used To Save New Student Record In Database */
	public static String saveUsersDetailsInDB(UsersManagedBean newUserObj) {
		int saveResult = 0;
		String navigationResult = "";
		try {      
			pstmt = getConnection().prepareStatement("insert into users (IIN, FULLNAME, PHONE) values (?, ?, ?)");			
			pstmt.setInt(1, newUserObj.getIin());
			pstmt.setString(2, newUserObj.getFullname());
			pstmt.setString(3, newUserObj.getPhone());
			saveResult = pstmt.executeUpdate();
			connObj.close();
		} catch(Exception sqlException) {
                    System.out.println(sqlException.toString());
			sqlException.printStackTrace();
		}
		if(saveResult !=0) {
			navigationResult = "usersList.xhtml?faces-redirect=true";
		} else {
			navigationResult = "createUser.xhtml?faces-redirect=true";
		}
		return navigationResult;
	}

	/* Method Used To Edit Student Record In Database */
	public static String editUsersRecordInDB(int userId) {
		UsersManagedBean editRecord = null;
		System.out.println("editStudentRecordInDB() : User Id: " + userId);

		/* Setting The Particular Student Details In Session */
		Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		try {
			stmtObj = getConnection().createStatement();    
			resultSetObj = stmtObj.executeQuery("select * from users where id = "+userId);    
			if(resultSetObj != null) {
				resultSetObj.next();
				editRecord = new UsersManagedBean(); 
				editRecord.setId(resultSetObj.getInt("ID"));
				editRecord.setIin(resultSetObj.getInt("IIN"));
				editRecord.setFullname(resultSetObj.getString("FULLNAME"));
				editRecord.setPhone(resultSetObj.getString("PHONE"));
			}
			sessionMapObj.put("editRecordObj", editRecord);
			connObj.close();
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/editUser.xhtml?faces-redirect=true";
	}

	/* Method Used To Update Student Record In Database */
	public static String updateUsersDetailsInDB(UsersManagedBean updateUserObj) {
		try {
			pstmt = getConnection().prepareStatement("update users set iin=?, fullname=?, phone=? where id=?");    
			pstmt.setInt(1,updateUserObj.getIin());  
			pstmt.setString(2,updateUserObj.getFullname());  
			pstmt.setString(3,updateUserObj.getPhone());    
			pstmt.setInt(4,updateUserObj.getId());  
			pstmt.executeUpdate();
			connObj.close();			
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/usersList.xhtml?faces-redirect=true";
	}

	/* Method Used To Delete Student Record From Database */
	public static String deleteUsersRecordInDB(int userId){
		System.out.println("deleteUsersRecordInDB() : User Id: " + userId);
		try {
			pstmt = getConnection().prepareStatement("delete from users where id = "+userId);  
			pstmt.executeUpdate();  
			connObj.close();
		} catch(Exception sqlException){
			sqlException.printStackTrace();
		}
		return "/usersList.xhtml?faces-redirect=true";
	}
        
        // ---  Продукты
        
        public static String getUserFullName(int userID) {
            String fio = "";
            try {
			Statement stmtObj2 = getConnection().createStatement();    
			ResultSet resultSetObj2 = stmtObj2.executeQuery("select * from users where id='"+userID+"'");    
			if(resultSetObj2.next()) {  
                            fio = resultSetObj2.getString("fullname");
			}   
                        resultSetObj2.close();
                        stmtObj2.close();
		} catch(Exception sqlException) {			
			System.out.println("getUserFullName exception: " + sqlException);
		} 
            return fio;
        }
        
        public static List<String> getAllUsers() {
            List<String> users = new ArrayList<String>();
            try {
			Statement stmtObj2 = getConnection().createStatement();    
			ResultSet resultSetObj2 = stmtObj2.executeQuery("select * from users");    
			while(resultSetObj2.next()) {  
                            users.add(resultSetObj2.getString("fullname"));
			}   
                        resultSetObj2.close();
                        stmtObj2.close();
		} catch(Exception sqlException) {			
			System.out.println("getUserFullName exception: " + sqlException);
		} 
            return users;
        }
        
        /* Method To Fetch The Student Records From Database */
	public static ArrayList<ProductsManagedBean> getProductsListFromDB() {
		ArrayList<ProductsManagedBean> productsList = new ArrayList<ProductsManagedBean>();  
		try {
			stmtObj = getConnection().createStatement();    
			resultSetObj = stmtObj.executeQuery("select * from products");    
			while(resultSetObj.next()) {  
				ProductsManagedBean stuObj = new ProductsManagedBean(); 
                                stuObj.setId(resultSetObj.getInt("id"));
				stuObj.setContract(resultSetObj.getString("contract"));  
				stuObj.setType(resultSetObj.getString("type"));  
                                stuObj.setUserId(resultSetObj.getInt("user_id"));
				stuObj.setUserFIO(getUserFullName(resultSetObj.getInt("user_id")));  
				stuObj.setStartContartDate(resultSetObj.getDate("START_CONTRACT_DATE"));  
				productsList.add(stuObj);  
			}   
			System.out.println("Total Records Fetched: " + productsList.size());
			connObj.close();
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} 
		return productsList;
	}

	/* Method Used To Save New Student Record In Database */
	public static String saveProductsDetailsInDB(ProductsManagedBean newProductObj) {
            System.out.println("saveProductsDetailsInDB "+newProductObj.getContract());
		int saveResult = 0;
		String navigationResult = "";
		try {      
			pstmt = getConnection().prepareStatement("insert into products (contract, type, user_id,START_CONTRACT_DATE) values (?, ?, ?,?)");			
			pstmt.setString(1, newProductObj.getContract());
			pstmt.setString(2, newProductObj.getType());
			pstmt.setInt(3, getUserIdByUserName(newProductObj.getUserFIO()));
                        pstmt.setDate(4, new java.sql.Date(newProductObj.getStartContartDate().getTime()));
			saveResult = pstmt.executeUpdate();
			connObj.close();
		} catch(Exception sqlException) {
			System.out.println(sqlException.toString());
		}
		if(saveResult !=0) {
			navigationResult = "productsList.xhtml?faces-redirect=true";
		} 
		return navigationResult;
	}

        public static int getUserIdByUserName(String userName) {
            int userId = 0;
            try {
			Statement stmtObj2 = getConnection().createStatement();    
			ResultSet resultSetObj2 = stmtObj2.executeQuery("select * from users where fullname='"+userName+"'");
                        
			if(resultSetObj2.next()) {  
                           userId = resultSetObj2.getInt("id");
			}   
                        resultSetObj2.close();
                        stmtObj2.close();
		} catch(Exception sqlException) {			
			System.out.println("getUserIdByUserName exception: " + sqlException);
		} 
            return userId;
        }
        
	/* Method Used To Edit Student Record In Database */
	public static String editProductsRecordInDB(ProductsManagedBean product) {
		System.out.println("editProductsRecordInDB() : Product Id: " + product.getId());
                String contract = "", type = "", userFIO = "";
                int userid = 0;
                java.util.Date startDate = null;
		try {
                    contract = product.getContract();
		    type = product.getType();
                    userid = product.getUserId();
                    userFIO = product.getUserFIO();
		    startDate = product.getStartContartDate();
                        
                    pstmt = getConnection().prepareStatement("update products set contract=?, type=?, user_id=?, START_CONTRACT_DATE=? where id=?");    
		    pstmt.setString(1,contract);  
		    pstmt.setString(2,type);  
		    pstmt.setInt(3,getUserIdByUserName(userFIO));    
                    pstmt.setDate(4,new java.sql.Date(startDate.getTime())); 
		    pstmt.setInt(5,product.getId());  
                    pstmt.executeUpdate();    
                        
		    connObj.close();
		} catch(Exception sqlException) {
			System.out.println("updatException="+sqlException.toString());
		}
		return "/productsList.xhtml?faces-redirect=true";
	}
        
	/* Method Used To Delete Student Record From Database */
	public static String deleteProductsRecordInDB(int productId){
		System.out.println("deleteProductsRecordInDB() : Product Id: " + productId);
		try {
			pstmt = getConnection().prepareStatement("delete from products where id = "+productId);  
			pstmt.executeUpdate();  
			connObj.close();
		} catch(Exception sqlException){
			sqlException.printStackTrace();
		}
		return "/productsList.xhtml?faces-redirect=true";
	}
}
