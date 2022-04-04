package Test;

import Utils.ExcelReader;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserTest {
    RequestSpecification requestSpecification;
    String BaseUri = "https://api-nodejs-todolist.herokuapp.com";

    // Registering user
    @Test
    public void RegisterUserTest() throws Exception{
        List<String> list = ExcelReader.GetCreateUserData(0, 1);
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
        System.out.println(ResponseBody);
        JsonPath jsonPath = new JsonPath(ResponseBody);
        String Token = jsonPath.getString("token");
        String ID = jsonPath.getString("user._id");
        System.out.println(Token);  // write that token in the excel sheet
        ExcelReader.TokenWriter(Token);
        ExcelReader.IDWriter(ID);
        List<Header> allValues = response.getHeaders().getList("Content-Type");
        assertThat(response.statusCode(), is(equalTo(201)));
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
    }

    // Login user
    @Test(priority = 1)
    public void LoginUserTest() throws Exception{
        List<String> list = ExcelReader.GetCreateUserData(0, 1);
        String email, password, name, id;
        int age;
        name = list.get(0);
        email = list.get(1);
        password = list.get(2);
        age = Integer.parseInt(list.get(3));
        id = list.get(5);
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
//        System.out.println(jsonPath.getString("token"));
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
        // validating user credentials
        assertThat(jsonPath.getString("user.name"), is(equalTo(name)));
        assertThat(jsonPath.getString("user.email"), is(equalTo(email)));
        assertThat(jsonPath.getString("user._id"), is(equalTo(id)));
    }

    // adding tasks
    @Test(priority = 2)
    public void AddTaskTest() throws Exception{
        List<String> list = ExcelReader.GetCreateUserData(0, 1);
        String taskNum = null;
        String userToken = list.get(4);
        String userID = list.get(5);
        int rowNum = 1;
        String header = "Bearer " + userToken;
        List<String> taskList = ExcelReader.TaskReader(1);
        for (int i=0; i< 20; i++) {
            taskNum = taskList.get(i);
            JSONObject parameter = new JSONObject();
            parameter.put("description", taskNum);
            requestSpecification = with().
                    baseUri(BaseUri).
                    body(parameter.toString()).
                    header("Content-Type", "application/json").
                    header("Authorization", header);
            Response response = requestSpecification.post("/task");
            List<Header> allValues = response.getHeaders().getList("Content-Type");
            String ResponseBody = response.asString();
            JsonPath jsonPath = new JsonPath(ResponseBody);
            String taskID = jsonPath.getString("data._id");
            ExcelReader.TaskIDWriter(taskID, rowNum);
            rowNum += 1;
//            System.out.println(response.statusCode());
            //validation
            assertThat(response.statusCode(), is(equalTo(201)));
            assertThat(allValues.get(0).getValue(), is(equalTo("application/json; charset=utf-8")));
//            assertThat(userID, is(equalTo(jsonPath.getString("data.owner"))));
        }
    }
    @Test(priority = 3)
    public void PageValidationTest() throws Exception{
        int num = 2;
        List<String> list = ExcelReader.GetCreateUserData(0, 1);
        String userToken = list.get(4);
        String header = "Bearer "+userToken;
        requestSpecification = with().
                baseUri(BaseUri).
                header("Content-Type", "application/json").
                header("Authorization", header);
        Response response = requestSpecification.get("task/?limit="+num);
        String responseBody = response.asString();
        JsonPath jsonPath = new JsonPath(responseBody);
//        System.out.println(response.statusCode());
//        System.out.println(jsonPath.getString("count"));
//        System.out.println(jsonPath.getString("count"));
        assertThat(jsonPath.getString("count"), is(equalTo("2")));
        assertThat(response.statusCode(), is(equalTo(200)));
    }
    //Negative testcases
    @Test(priority = 4)
    public void SameUserRegister() throws Exception{
        List<String> list = ExcelReader.GetCreateUserData(0, 1);
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
        List<Header> allValues = response.getHeaders().getList("Content-Type");
        assertThat(response.statusCode(), is(equalTo(400)));
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
    }
    @Test(priority = 5)
    public void InvalidUserLogin() throws Exception{
        String email, password;
        email = "all.might@gmail.com";
        password="myheroacademia";
        JSONObject parameter = new JSONObject();
        parameter.put("email", email);
        parameter.put("password", password);
        requestSpecification = with().
                baseUri(BaseUri).
                body(parameter.toString()).
                header("Content-Type", "application/json");
        Response response = requestSpecification.post("/user/login");
        List<Header> allValues = response.getHeaders().getList("Content-Type");
        assertThat(response.statusCode(), is(equalTo(400)));
        assertThat(allValues.get(0).getValue(), is(equalTo("application/json; charset=utf-8")));
    }

    @Test(priority = 6)
    public void InvalidRequestBody() throws Exception{
        List<String> getToken = ExcelReader.GetCreateUserData(0,1);
        String userToken = getToken.get(4);
        String header = "Bearer "+ userToken;
//        System.out.println(parameter.toString());
        requestSpecification = with().
                baseUri(BaseUri).
                body("description: invalid format").
                header("content-Type", "application/json").
                header("Authorization", header);
        Response response = requestSpecification.post("/task");
        assertThat(response.statusCode(), is(equalTo(400)));
    }
}
