package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Attachment;
import models.CreateBookRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DeleteBook {
    public String token;
    public Integer bookingid;
    Attachment attachment = new Attachment();

    @BeforeClass
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

    @BeforeClass
    public void createBook(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Map<String,String> bookingDatesMap = new HashMap<>();
        bookingDatesMap.put("checkin", "2021-07-01");
        bookingDatesMap.put("checkout", "2021-07-01");

        CreateBookRequest createBookRequest = new CreateBookRequest("Ali",
                "Veli",120,false,
                bookingDatesMap,
                "Lunch");

        RequestSpecification request = RestAssured.given()
                .log().all()
                .header("Content-Type", "application/json");

        Response response = request
                .body(createBookRequest)
                .when().post("/booking");
        Assert.assertEquals(response.getStatusCode(), 200);
        attachment.addAttachment(request, baseURI, response);
        bookingid = response.then().contentType(ContentType.JSON).extract().path("bookingid");
        System.out.println(bookingid);
    }

    @Test
    public void deleteBook(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        RequestSpecification request = RestAssured.given()
                .log().all()
                .header("Content-Type", "application/json")
                .cookie("token", ("\""+token+"\""));

        Response response = request.when().delete("/booking/" + bookingid);
        System.out.println(response.then().extract().asString());
        Assert.assertEquals(response.getStatusCode(), 201);
        attachment.addAttachment(request,baseURI,response);
    }

}
