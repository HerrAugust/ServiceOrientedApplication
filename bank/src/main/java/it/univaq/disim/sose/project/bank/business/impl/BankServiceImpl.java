package it.univaq.disim.sose.project.bank.business.impl;

import org.springframework.stereotype.Service;
import java.sql.*;

import it.univaq.disim.sose.project.bank.AccountResponse;
import it.univaq.disim.sose.project.bank.AddressType;
import it.univaq.disim.sose.project.bank.BankMessageType;
import it.univaq.disim.sose.project.bank.LoginType;
import it.univaq.disim.sose.project.bank.NameType;
import it.univaq.disim.sose.project.bank.accountRequest;
import it.univaq.disim.sose.project.bank.accountResponse;
import it.univaq.disim.sose.project.bank.withdrawRequest;
import it.univaq.disim.sose.project.bank.withdrawResponse;
import it.univaq.disim.sose.project.bank.refillRequest;
import it.univaq.disim.sose.project.bank.refillResponse;
import it.univaq.disim.sose.project.bank.business.BusinessException;
import it.univaq.disim.sose.project.bank.business.BankService;

@Service
public class BankServiceImpl implements BankService {

	String USER, PASS;
	
	@Override
	public accountResponse account(accountRequest parameters) throws BusinessException {
		AccountResponse response = new accountResponse();
		LoginType element = new loginType();
		
		String sentuser = paramenters.getAccount().username();
		String sentpass = paramenters.getAccount().password();
		
		Connection conn = null;
		Statement stmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM accounts JOIN addresses WHERE accounts.username = " + sentuser + " and accounts.password 0 " + sentpass;
			ResultSet rs = stmt.executeQuery(sql);

			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				String returnedusername = rs.getString("username");
				String returnpassword = rs.getString("password");
				double amount = rs.getDouble("amount");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String city = rs.getString("city");
				String street = rs.getString("street");
				String country = rs.getString("country"); 
				
				if(returnedusername.equals(sentuser)) {
					element.setUsername(returnedusername);
					element.setPassword(returnpassword);
					response.getAccount().add(element);
					response.setAmount(amount);
					response.setName(new NameType(firstname, lastname));
					response.setAddress(new AddressType(street, city, country));
				}
				else {
					//nothing returned to client
				}
			}
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try

		
		return response;
	}

	@Override
	public withdrawResponse withdraw(withdrawRequest parameters) throws BusinessException {
		withdrawResponse response = new withdrawResponse();
		withdrawMessageType element = new withdrawMessageType();
		element.setDesc("test");
		response.getTrafficMessage().add(element);
		return response;
	}

	@Override
	public refillResponse refill(refillRequest parameters) throws BusinessException {
		refillResponse response = new refillResponse();
		refillMessageType element = new refillMessageType();
		element.setDesc("test");
		response.getTrafficMessage().add(element);
		return response;
	}



}
