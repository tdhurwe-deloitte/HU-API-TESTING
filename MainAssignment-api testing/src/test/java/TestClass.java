import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestClass {
    String BaseUri = "https://api-nodejs-todolist.herokuapp.com";

    @Test
    public void CreateUserMethod(){
        File JsonData = new File("src/test/resources/RegisterUser.json");
        given().
                baseUri(BaseUri).
                body(JsonData).
                header("Content-Type", "application/json").
        when().
                post("/user/register").
        then().
                statusCode(201);
    }
    @Test(priority = 1)
    public void LoginUserMethod(){
        File JsonData = new File("src/test/resources/LoginUser.json");
        RequestSpecification requestSpecification = with().
                baseUri(BaseUri).
                body(JsonData).
                header("Content-Type", "application/json");
        Response response = requestSpecification.post("/user/login");
//        ResponseBody body = response.getBody();
//        System.out.println(body.asString());
        assertThat(response.statusCode(), is(equalTo(200)));
    }
}
