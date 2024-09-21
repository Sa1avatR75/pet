Feature: Test Seller API functionality

  @getSellerVerifyEmailNotEmpty
  @regression
  Scenario: get a single seller and verify seller email is not empty
    Given user hits get single seller api with "/api/myaccount/sellers/" with id 5662
    Then verify seller email is not empty


  @getAllSellersVerifyIdMore0
  @regression
  Scenario: get all sellers and verify sellers emails are not empty
    Given user hits get all sellers api with "/api/myaccount/sellers"
    Then verify sellers verify sellers ids are not equals 0

  @updateSeller
  @regression
  Scenario: get single seller, update the same seller, verify seller was updated
    Given user hits get single seller api with "/api/myaccount/sellers/" with id 5056
    Then verify seller email is not empty
    Then user hits put api with "/api/myaccount/sellers/" with id 5056
    Then verify seller email was update
    Then verify seller first name was updated

  @regression
  Scenario: get seller, archive seller and then verify the seller is archived
    Given user hits get single seller api with "/api/myaccount/sellers/" with id 5056
    Then user hits post api with "/api/myaccount/sellers/archive/unarchive" to archive
    And user hits get all archived api "/api/myaccount/sellers"
    And verify seller is archived


    @regression
    Scenario: create seller and delete seller
      Given user hits post api with "/api/myaccount/sellers" to create
      And verify seller id was generated
      And verify seller name is not empty
      And verify seller email is not empty
      
      Then  user hits get all sellers api with "/api/myaccount/sellers"
#      Then user hits get all sellers api with "/api/myaccount/sellers"
      And verify seller was deleted