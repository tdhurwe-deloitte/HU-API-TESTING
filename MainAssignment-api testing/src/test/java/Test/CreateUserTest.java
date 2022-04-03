package Test;

import Utils.ExcelReader;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import  io.restassured.path.json.JsonPath.*;
import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import Utils.ExcelReader.*;

public class CreateUserTest {
    RequestSpecification requestSpecification;
    String BaseUri = "https://api-nodejs-todolist.herokuapp.com";

    // Registering user
    @Test
    public void RegisterUserTest() throws Exception{
        List<String> list = ExcelReader.GetCreateUserData(0);
        String name, email, password;
        int age;
        name = list.get(0);
        email = list.get(1);
        password = list.get(2);
        age = Integer.parseInt(list.get(3));
        JSONObject parameter = new JSONObject();
        parameter.put("name", name);
        parameter.put("email", email);
        parameter.put("password", password);
        parameter.put("age", age);
        requestSpecification = with().
                baseUri(BaseUri).
                body(parameter.toString())
                .header("Content-Type", "application/json");
        Response response = requestSpecification.post("/user/register");
        String ResponseBody = response.asString();
//        System.out.println(ResponseBody);
        JsonPath jsonPath = new JsonPath(ResponseBody);
        String Token = jsonPath.getString("token");
        System.out.println(Token);  // write that token in the excel sheet
        List<Header> allValues = response.getHeaders().getList("Content-Type");
        assertThat(response.statusCode(), is(equalTo(201)));
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
    }

    // Login user
    @Test(priority = 1)
    public void LoginUserTest() throws Exception{
        List<String> list = ExcelReader.GetCreateUserData(0);
        String email, password, name;
        int age;
        name = list.get(0);
        email = list.get(1);
        password = list.get(2);
        age = Integer.parseInt(list.get(3));
        JSONObject parameter = new JSONObject();
        parameter.put("email", email);
        parameter.put("password", password);
        requestSpecification = with().
                baseUri(BaseUri).
                body(parameter.toString()).
                header("Content-Type", "application/json");

        Response response = requestSpecification.post("/user/login");
        List<Header> allValues = response.getHeaders().getList("Content-Type");
        String ResponseBody = response.asString();
        JsonPath jsonPath = new JsonPath(ResponseBody);
        System.out.println(jsonPath.getString("token"));
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
        // validating user credentials
        assertThat(jsonPath.getString("user.name"), is(equalTo(name)));
        assertThat(jsonPath.getString("user.email"), is(equalTo(email)));
    }

    // adding tasks
    @Test(priority = 2)
    public void AddTaskTest() throws Exception{
//        List<String> TaskList = ExcelReader.GetCreateTaskData(1);
        String Token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjQ5NjRkMzE2YTc0ZTAwMTc0NWZmZGQiLCJpYXQiOjE2NDg5OTIzNjF9.Hxy6Sdlc2fMRKYiHiPZ-K2SBq6JReaJazB465h6hJjg";
        JSONObject parameter = new JSONObject();
        parameter.put("description", "Task2");
        requestSpecification = with().
                baseUri(BaseUri).
                body(parameter.toString()).
                header("Content-Type", "application/json").
                header("Authorization", Token);
        Response response = requestSpecification.post("/task");
        List<Header> allValues = response.getHeaders().getList("Content-Type");
        String ResponseBody = response.asString();
        JsonPath jsonPath = new JsonPath(ResponseBody);
        assertThat(response.statusCode(), is(equalTo(201)));
        assertThat(allValues.get(0).getValue(), is(equalTo("application/json; charset=utf-8")));
        System.out.println(jsonPath.getString("data._id"));
    }
}
