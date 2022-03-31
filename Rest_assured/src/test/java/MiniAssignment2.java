import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MiniAssignment2 {
    RequestSpecification requestSpecification;
    @Test
    public void testGetCall(){
        requestSpecification = with().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type", "application/json");
        Response response = requestSpecification.get("/posts");
        List<Header> allValues = response.getHeaders().getList("Content-Type");
//        Status code assertion
        assertThat(response.statusCode(), equalTo(200));
//        content type assertion
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
    }

    @Test
    public void testPutCall(){
        requestSpecification = with().
                baseUri("https://reqres.in/api/").
                header("Content-Type", "application/json");

        Response response = requestSpecification.get("/users");
        List<Header> allValues = response.getHeaders().getList("Content-Type");
//        Status code assertion
        assertThat(response.statusCode(), equalTo(200));
//        content type assertion
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
    }
}
