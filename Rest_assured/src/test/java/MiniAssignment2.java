import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONArray;
import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertTrue;

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
        JSONArray arr = new JSONArray(response.asString());

        List<Header> allValues = response.getHeaders().getList("Content-Type");
//        Status code assertion
        assertThat(response.statusCode(), equalTo(200));
//        content type assertion
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
        assertTrue((int)arr.getJSONObject(39).get("id")== 40 && ((int)arr.getJSONObject(39).get("userId"))== 4);
        String str = "";
        for (int i= 0; i< arr.length(); i++) {
            if (arr.getJSONObject(i).get("title").getClass().getSimpleName() != str.getClass().getSimpleName()) {
                assertTrue(false);

            }
        }
    }

    @Test
    public void testPutCall(){
        File jsonData = new File("src/main/resources/putData.json");
        requestSpecification = with().
                baseUri("https://reqres.in/api").body(jsonData).
                header("Content-Type", "application/json");

        Response response = requestSpecification.get("/users");
//        ResponseBody body = response.getBody();
//        String bodyAsString = body.asString();
//        System.out.println(bodyAsString);
        List<Header> allValues = response.getHeaders().getList("Content-Type");
        //        Status code assertion
        assertThat(response.statusCode(), equalTo(200));
//        content type assertion
        assertThat(allValues.get(0).getValue(), equalTo("application/json; charset=utf-8"));
        given().
                spec(requestSpecification).
                when().put("/users").
                then().body("name",equalTo("Arun")).body("job",equalTo("Manager"));
    }
}