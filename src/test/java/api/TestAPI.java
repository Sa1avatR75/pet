package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.*;

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

    // post seller
    @Test
    public void postSeller(){
        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();

        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().name());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().streetAddress());

        Response response = RestAssured
                .given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .post("/api/myaccount/sellers");
        response.prettyPrint();

        Assert.assertEquals(201, response.getStatusCode());

    }
    @Test
    public void verifyPostSeller(){
        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().name());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().streetAddress());

        Response response = RestAssured
                .given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .post("/api/myaccount/sellers");
        response.prettyPrint();

        String id = response.jsonPath().getString("seller_id");
        System.out.println(id);


        Response getResponse = RestAssured.given().auth().oauth2(CashWiseToken.getToken()).baseUri(Config.getProperty("cashwiseApiUrl")).get("/api/myaccount/sellers/" + id);
        getResponse.prettyPrint();
        Assert.assertEquals(200, getResponse.getStatusCode());



    }


    @Test
    public void createSellerNoEmail(){
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("Company12342Withnoemail");
        requestBody.setSeller_name("NoEmailSellerName");
        requestBody.setPhone_number("23123123");
        requestBody.setAddress("address");
        Response response = RestAssured.given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .contentType(ContentType.JSON).body(requestBody).post("/api/myaccount/sellers");
        response.prettyPrint();
    }


    @Test
    public void archiveSeller(){
        HashMap<String , Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", "765");
        params.put("archive", true);
        Response response =
                RestAssured.given().auth().oauth2(CashWiseToken.getToken())
                        .baseUri(Config.getProperty("cashwiseApiUrl"))
                        .params(params).post("/api/myaccount/sellers/archive/unarchive");

        response.prettyPrint();

    }

    @Test
    public void getAllUnrchivedSellers() throws JsonProcessingException {

        HashMap<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page",1);
        params.put("size", 100);

        Response response = RestAssured.given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .params(params).get("/api/myaccount/sellers");

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


        HashMap<String, Object> archiveHashMap = new HashMap<>();
        archiveHashMap.put("archive", true);

        for (int i = 0; i < customResponse.getResponses().size(); i++) {
           int id = customResponse.getResponses().get(i).getSeller_id();
            archiveHashMap.put("sellersIdsForArchive", id);
            Response archiveAllSellersResponse = RestAssured.given()
                    .auth().oauth2(CashWiseToken.getToken())
                    .baseUri(Config.getProperty("c"))
                    .params(archiveHashMap).post("/api/myaccount/sellers/archive/unarchive");
            archiveAllSellersResponse.prettyPrint();
        }
    }


    @Test
    public void unarchiveHotmail() throws JsonProcessingException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("isArchived", true);
        params.put("size", 100);
        params.put("page", 1);
        Response getResponse = RestAssured.given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .params(params)
                .get("/api/myaccount/sellers");

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(getResponse.asString(), CustomResponse.class);


        HashMap<String, Object> unarchiveEmailshashMap = new HashMap<>();
        unarchiveEmailshashMap.put("archive", false);

        for (int i = 0; i < customResponse.getResponses().size(); i++) {
            String email = customResponse.getResponses().get(i).getEmail();

            if ( email != null && email.endsWith("@hotmail.com") ){
                int id = customResponse.getResponses().get(i).getSeller_id();
                unarchiveEmailshashMap.put("sellersIdsForArchive", id);
                Response unarchiveEmail = RestAssured.given()
                        .auth().oauth2(CashWiseToken.getToken())
                        .baseUri(Config.getProperty("cashwiseApiUrl"))
                        .params(unarchiveEmailshashMap)
                        .post("/api/myaccount/sellers/archive/unarchive");
                unarchiveEmail.prettyPrint();
            }
        }

    }


    @Test
    public void verifyCreatingNewSeller() throws JsonProcessingException {
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("Sellers Company12345");
        requestBody.setSeller_name("dcdscdsv");
        requestBody.setEmail("verifycreatingaSascelelr@email.com");
        requestBody.setPhone_number("3023907893");

        Response response = RestAssured.given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .contentType(ContentType.JSON)
                .body(requestBody).post("/api/myaccount/sellers");
        response.prettyPrint();
        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse  =  mapper.readValue(response.asString(), CustomResponse.class);
        int id = customResponse.getSeller_id();
        Assert.assertEquals(201,response.getStatusCode());

        // get all sellers


        HashMap<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("size", 100);
        params.put("page", 1);
        Response getAllSellersResponse = RestAssured.given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .params(params)
                .get("/api/myaccount/sellers");

        CustomResponse allSellersCustomResponse = mapper.readValue(getAllSellersResponse.asString(), CustomResponse.class);
        Assert.assertEquals(200, getAllSellersResponse.getStatusCode());
        int actualID = 0;
        for (int i = 0; i < allSellersCustomResponse.getResponses().size(); i++) {
            if (id == allSellersCustomResponse.getResponses().get(i).getSeller_id()){
                actualID =  allSellersCustomResponse.getResponses().get(i).getSeller_id();
            }
        }
        Assert.assertEquals(id, actualID);
    }
}

