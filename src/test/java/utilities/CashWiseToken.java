package utilities;

import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CashWiseToken {
    public static String getToken(){
        String endPoint = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/auth/login";

        RequestBody requestBody = new RequestBody();
        requestBody.setEmail("salavatBatch6@gmail.com");
        requestBody.setPassword("admin123");
//        requestBody.setEmail("salavatBatch6@gmail.com");
//        requestBody.setPassword("admin123");

        Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(endPoint);
        return response.jsonPath().getString("jwt_token");
    }
}
