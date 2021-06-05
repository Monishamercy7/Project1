package com.ty.rmgyantra.projectTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ty.rmgyantra.genericLib.DataBaseUtilities;
import com.ty.rmgyantra.genericLib.JavaUtility;
import com.ty.rmgyantra.pojolib.Project;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.sql.ResultSet;
import java.sql.SQLException;



public class AddSingleProjectWithOngoingStatus {
	@Test
	public void addSingleProjectWithCreatedStatus() throws Throwable
	
	{
		DataBaseUtilities dbLib = new DataBaseUtilities();
		String projName = "Samsung_"+JavaUtility.getRandomData();
		
		Project pobj = new Project("Monisha", projName, "created", 7);
		
		Response resp = given()                           //precondition
		.contentType(ContentType.JSON)
		.body(pobj)
		.when()                           // actual http test
		.post("http://localhost:7777/addProject");
		
		resp.then()                            // validation
		.log().all()
		.and()
		.contentType(ContentType.JSON);
		
		// capture Project Name & Status
		String apiProjectName = resp.jsonPath().get("projectName");
		String apiStatus = resp.jsonPath().get("status");
	
		
		//write selenium program to capture data from GUI
		
		// Write JDBC program to capture data from MySql DB
		dbLib.connectToDB();
		boolean projectNameResult = dbLib.executeQueryAndGetData("select * from project", 4, apiProjectName);
		Assert.assertTrue(projectNameResult);
		boolean projectStatusResult = dbLib.executeQueryAndGetData("select * from project", 5, apiStatus);
		Assert.assertTrue(projectStatusResult);
		dbLib.closeDb();
	}

}
