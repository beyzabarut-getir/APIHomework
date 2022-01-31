package models;

import io.qameta.allure.Allure;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Attachment {

    public String addAttachment(RequestSpecification httpRequest, String url, Response response){
        String html = "Url= " + url + "\n\n" +
                "request header=" +((RequestSpecificationImpl) httpRequest).getHeaders() + "\n\n" +
                "request body=" +((RequestSpecificationImpl) httpRequest).getBody() + "\n\n" +
                "request body=" + response.getBody().asString();
        Allure.addAttachment("request detail", html);
        return html;
    }
}
