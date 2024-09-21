package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class APIRunner {
    @Getter
    private static CustomResponse customResponse;

    //
    public static void runGET(String path){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;

        Response response = RestAssured.given()
                .auth().oauth2(token).get(url);
        System.out.println("Status code" + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        try {
             customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("this is list response ");
        }
    }

    //GET API with params
    public static void runGET(String path, Map<String, Object> params){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;

        Response response = RestAssured.given()
                .auth().oauth2(token)
                .params(params)
                .get(url);

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Status code" + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("this is single response ");
        }
    }

    public static void runPOST(String path, RequestBody requestBody){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;

        Response response = RestAssured.given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Status code" + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("");
        }


    }

    public static void runPOST(String path, Map<String, Object> params){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;
        Response response = RestAssured.given()
                .auth().oauth2(token)
                .params(params)
                .post(url);

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Status code" + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("");
        }
    }

    public static void runDELETE(String path){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;

        Response response = RestAssured.given()
                .auth().oauth2(token)
                .delete(url);
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Status code" + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("");
        }
    }
    public static void runPUT(String path, RequestBody requestBody){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashwiseApiUrl") + path;

        Response response = RestAssured.given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put(url);

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Status code" + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("");
        }
    }
}
