package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class POJOTest {
    @Test
    public void createCategory() throws JsonProcessingException {

        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();
        requestBody.setCategory_title(faker.name().name());
        requestBody.setCategory_description("desk");
        requestBody.setFlag(false);

        Response response = RestAssured.given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("c");


        ObjectMapper objectMapper = new ObjectMapper();
        CustomResponse customResponse = objectMapper.readValue(response.asString(), CustomResponse.class);
        response.prettyPrint();
        Assert.assertEquals(201, response.getStatusCode());
    }


    @Test
    public void getSingleCategoryById() throws JsonProcessingException {
        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();
        ObjectMapper mapper = new ObjectMapper();


        requestBody.setCategory_title(faker.name().name());
        requestBody.setCategory_description(faker.name().lastName());
        requestBody.setFlag(true);

        Response postResponse = RestAssured
                .given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/api/myaccount/categories");

        CustomResponse customResponse = mapper.readValue(postResponse.asString(), CustomResponse.class);
        int requiredCategoryId = customResponse.getCategory_id();

        Response getResponse = RestAssured
                .given()
                .auth().oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .get("/api/myaccount/categories/" + requiredCategoryId);

        customResponse = mapper.readValue(getResponse.asString(), CustomResponse.class);

        int actualCategoryId = customResponse.getCategory_id();
        Assert.assertEquals(requiredCategoryId, actualCategoryId);

    }

    @Test
    public void create15Sellers() throws JsonProcessingException {
        RequestBody requestBody = new RequestBody();
        Faker faker = new Faker();
        ObjectMapper mapper = new ObjectMapper();
//        List<Integer> listOfIdsToArchive = List/.of(1223, 3234);
        CustomResponse customResponse;
//        for (int i = 0; i < 15; i++) {
//            requestBody.setCompany_name(faker.name().name());
//            requestBody.setSeller_name(faker.name().lastName());
//            requestBody.setEmail(faker.internet().emailAddress());
//            requestBody.setPhone_number(faker.phoneNumber().cellPhone());
//            requestBody.setAddress(faker.address().streetAddress());
//
//            Response postResponse = RestAssured
//                    .given()
//                    .auth().oauth2(CashWiseToken.getToken())
//                    .baseUri(Config.getProperty("cashwiseApiUrl"))
//                    .contentType(ContentType.JSON)
//                    .body(requestBody)
//                    .post("/api/myaccount/sellers");
//            Assert.assertEquals(201, postResponse.getStatusCode());
//
//            customResponse = mapper.readValue(postResponse.asString(), CustomResponse.class);
//            listOfIdsToArchive.add(customResponse.getSeller_id());
//        }
//        System.out.println(listOfIdsToArchive);
//

//        listOfIdsToArchive = List.of(411, 5412, 5413, 5414, 5415, 5416, 5417, 5418, 5419, 5420, 5421, 5422, 5423, 5424, 5425);
        int[] ids = {5411, 5412, 5413, 5414, 5415, 5416, 5417, 5418, 5419, 5420, 5421, 5422, 5423, 5424, 5425};


//        HashMap<String, Object> params = new HashMap<>();
//        params.put("sellersIdsForArchive", Arrays.asList(ids));
////        params.put("sellersIdsForArchive", 5411);
//        params.put("archive", true);

        Response postToArchive = RestAssured
                .given().auth()
                .oauth2(CashWiseToken.getToken())
                .baseUri(Config.getProperty("cashwiseApiUrl"))
                .queryParam("sellersIdsForArchive", Arrays.asList(ids))
                .queryParam("archive", true)
                .post("/api/myaccount/sellers/archive/unarchive");

        postToArchive.prettyPrint();
        Assert.assertEquals(200, postToArchive.getStatusCode());
    }
}
