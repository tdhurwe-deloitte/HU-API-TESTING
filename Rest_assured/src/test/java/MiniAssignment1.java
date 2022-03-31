import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class MiniAssignment1 {
    @Test
    public void testGetCall(){
        Response response = RestAssured
                .given()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .extract()
                .response();
        List<Header> allValue = response.getHeaders().getList("Content-Type");
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type", "application/json").
        when().
                get("/posts").
        then().
                body("id", hasItem(40), "[39].userId", is(equalTo(4))).
                statusCode(200);
    }

    @Test
    public void testPutCall(){
        File jsonData = new File("src/main/resources/putData.json");
        Response response = RestAssured
                .given()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .extract()
                .response();
        List<Header> allValue = response.getHeaders().getList("Content-Type");
        given().
                baseUri("https://reqres.in/api").
                body(jsonData).
                header("Content-Type", "application/json").
        when().
                put("/users").
        then().
                body("name",is(equalTo("Arun")), "job", is(equalTo("Manager"))).
                statusCode(200);

        for(Header header : allValue)
        {
            System.out.print(header.getName() +" : ");
            System.out.println(header.getValue());
        }
    }
}
