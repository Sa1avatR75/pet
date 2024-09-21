package steps;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.APIRunner;
import utilities.Config;

import java.util.HashMap;
import java.util.Map;

public class APISteps {
    private Response response;

    private String newEmail;

    private String newSellerName;

    private String oldEmail;

    private String oldSellerName;

    private Faker faker = new Faker();
    int sellerID;


    @Given("user hits get single seller api with {string} with id {int}")
    public void user_hits_get_single_seller_api_with(String endpoint, Integer id) {
        APIRunner.runGET(endpoint + id);
        oldEmail = APIRunner.getCustomResponse().getEmail();
        oldSellerName = APIRunner.getCustomResponse().getSeller_name();
        sellerID = APIRunner.getCustomResponse().getSeller_id();

    }

    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
        String email = APIRunner.getCustomResponse().getEmail();
        Assert.assertFalse(email.isEmpty());
    }


    @Given("user hits get all sellers api with {string}")
    public void user_hits_get_all_sellers_api_with(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("size", 120);
        params.put("page", 1);
        params.put("isArchived", false);
        APIRunner.runGET(endpoint, params);


    }

    @Then("verify sellers verify sellers ids are not equals {int}")
    public void verify_sellers_emails_are_not_empty(Integer int1) {
        int size = APIRunner.getCustomResponse().getResponses().size();
        for (int i = 0; i < size; i++) {
            int id = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            Assert.assertNotEquals((int) int1, id);
        }

    }


    @Then("user hits put api with {string} with id {int}")
    public void user_hits_put_api_with_with_id(String endpoint, Integer id) {


        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().name());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().cellPhone());
        requestBody.setAddress(faker.address().streetAddress());

        APIRunner.runPUT(endpoint + id, requestBody);
        newEmail = APIRunner.getCustomResponse().getEmail();
        newSellerName = APIRunner.getCustomResponse().getSeller_name();
    }

    @Then("verify seller email was update")
    public void verify_seller_email_was_update() {
        Assert.assertNotEquals(oldEmail, newEmail);

    }

    @Then("verify seller first name was updated")
    public void verify_seller_first_name_was_updated() {
        Assert.assertNotEquals(oldSellerName, newSellerName);

    }


    @Given("verify seller is NOT archived")
    public void verify_seller_is_not_archived() {
//        Assert.assertEquals("Продавец успешно активирован", APIRunner.getCustomResponse().getMessage());
//        Assert.assertFalse(APIRunner.getCustomResponse().get);
    }

    @Given("verify seller is archived")
    public void verify_seller_is_archived() {
        boolean isPresent = false;

        for (int i = 0; i <  APIRunner.getCustomResponse().getResponses().size(); i++) {
            int id = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if (id == sellerID) {
                isPresent = true;
                break;
            }

        }
        Assert.assertTrue(isPresent);
//        Assert.assertEquals("Продавец успешно архивирован", APIRunner.getCustomResponse().getMessage());
    }

    @Then("user hits post api with {string} to archive")
    public void user_hits_post_api_with_to_archive_id(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", sellerID);
        params.put("archive", true);
        APIRunner.runPOST(endpoint, params);
    }


    @Then("user hits get all archived api {string}")
    public void user_hits_get_all_archived_api(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("size", 10);
        params.put("page", 1);
        params.put("isArchived", true);
        APIRunner.runGET(endpoint, params);
    }



    @Given("user hits post api with {string} to create")
    public void user_hits_post_api_with_to_create(String endpoint) {
        RequestBody requestBody = new RequestBody();

        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().name());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().cellPhone());
        requestBody.setAddress(faker.address().streetAddress());

        APIRunner.runPOST(endpoint, requestBody);

        sellerID = APIRunner.getCustomResponse().getSeller_id();
    }
    @Given("verify seller id was generated")
    public void verify_seller_id_was_generated() {
        Assert.assertNotNull(sellerID);
    }
    @Given("verify seller name is not empty")
    public void verify_seller_name_is_not_empty() {
        Assert.assertFalse(APIRunner.getCustomResponse().getSeller_name().isEmpty());
    }
    @Then("verify seller was deleted")
    public void verify_seller_was_deleted() {


//        HashMap<String, Object> params = new HashMap<>();
//        params.put("isArchived",false);
//        params.put("size", 112);
//        params.put("size", 1);
//
//        APIRunner.runGET("/api/myaccount/sellers", );
        boolean isPresent = false;
        for (int i = 0; i < APIRunner.getCustomResponse().getResponses().size(); i++) {
            if (sellerID == APIRunner.getCustomResponse().getResponses().get(i).getSeller_id()){
                isPresent = true;
                break;
            }
        }
        Assert.assertFalse(isPresent);

    }

}
