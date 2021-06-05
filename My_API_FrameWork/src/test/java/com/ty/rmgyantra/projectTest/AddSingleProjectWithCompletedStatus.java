package com.ty.rmgyantra.projectTest;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ty.rmgyantra.genericLib.BaseClass;
import com.ty.rmgyantra.genericLib.EndPoint;
import com.ty.rmgyantra.genericLib.JavaUtility;
import com.ty.rmgyantra.pojolib.Project;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AddSingleProjectWithCompletedStatus extends BaseClass {
	
	@Test
	public void addSingleProjectWithCompletedStatus() throws Throwable {

		
		String projNAme = "JhonyWalkerWhisky_"+JavaUtility.getRandomData();
		
		Project pobj = new Project("Monisha", projNAme, "Completed", 10);
		
		Response resp = given()                               // pre conditions
						  .contentType(ContentType.JSON)
						  .body(pobj)
						.when()                             // actual http test
						   .post(EndPoint.addProject);
		
		resp.then()
		     .log().all()
		     .and()
		     .contentType(ContentType.JSON);
		
		//capture Project NAme & status
		    String apiProjecName  = resp.jsonPath().get("projectName");
		    String apiStatus  = resp.jsonPath().get("status");
					  
	     //write Selenium program to capture data from GUI
		    
		 //Write JDBC program to capture data from Mysql DB
		 
		    boolean projectNAmeresult = dbLib.executeQueryAndGetData("select * from project" ,4 , apiProjecName);
		    Assert.assertTrue(projectNAmeresult);
		    boolean projectStatus = dbLib.executeQueryAndGetData("select * from project" ,5 , apiStatus);
		    Assert.assertTrue(projectStatus);
	}
}
