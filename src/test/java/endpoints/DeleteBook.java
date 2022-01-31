package endpoints;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.CreateBookRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DeleteBook {
    public String token;
    public Integer bookingid;
    public String url = "https://restful-booker.herokuapp.com";

    @BeforeMethod
    public void createToken(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .when().post("/auth");
        token = response.then().contentType(ContentType.JSON).extract().path("token").toString();
    }


    @Test
    public void createBook(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Map<String,String> bookingDatesMap = new HashMap<>();
        bookingDatesMap.put("checkin", "2021-07-01");
        bookingDatesMap.put("checkout", "2021-07-01");

        CreateBookRequest createBookRequest = new CreateBookRequest("Ali",
                "Veli",120,false,
                bookingDatesMap,
                "Lunch");

        String request = new Gson().toJson(createBookRequest);
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(request)
                .when().post("/booking");

        String stringResponse = response.then()
                .assertThat().statusCode(200)
                .extract().response().asString();
        System.out.println(stringResponse);
        bookingid = response.then().contentType(ContentType.JSON).extract().path("bookingid");
        System.out.println(bookingid);
    }

    @Test
    public void deleteBook(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String response = given().log().all()
                .header("Content-Type", "application/json")
                .cookie("token", ("\""+token+"\""))
                .when().delete("/booking/22")
                .then().assertThat().log().all().statusCode(201)
                .extract().response().asString();
        System.out.println(response);
    }

}
