package api;

import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAPI {



    @Test
    public void getSingleSeller(){
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/" + 4611;

        String token = CashWiseToken.getToken();

        Response response = RestAssured.given().auth().oauth2(token).get(url);

        String expectedEmail = response.jsonPath().getString("email");

        Assert.assertTrue(expectedEmail.endsWith(".com"));
        Assert.assertNotNull(expectedEmail);
    }

    @Test
    public void getAllSellers(){
        String endPoint = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token =  CashWiseToken.getToken();

        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("isArchived", false);
        queryParam.put("page", 1);
        queryParam.put("size", 10);

        Response response = RestAssured.given().auth().oauth2(token).queryParams(queryParam).get(endPoint);


        Assert.assertEquals(200, response.getStatusCode());
        String email1 = response.jsonPath().getString("responses[0].email");
        String email3 = response.jsonPath().getString("responses[2].email");
        String email5 = response.jsonPath().getString("responses[4].email");


        Assert.assertFalse(email1.isEmpty());
        Assert.assertFalse(email3.isEmpty());
        Assert.assertFalse(email5.isEmpty());

//        System.out.println(response.jsonPath().getString("responses.email"));
        System.out.println(response.jsonPath().getString("responses.email"));
    }

    @Test
    public void getAllSellersLoop(){
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.getToken();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("isArchived", false);
        queryParams.put("size", 10);
        queryParams.put("page",1);

        Response response = RestAssured.given().auth().oauth2(token).queryParams(queryParams).get(url);
        Assert.assertEquals(200, response.getStatusCode());
//        response.prettyPrint();

        List<String> listOfEmail = response.jsonPath().getList("responses.email");

        for (String email: listOfEmail) {
            Assert.assertFalse(email.isEmpty());
        }
    }
}
