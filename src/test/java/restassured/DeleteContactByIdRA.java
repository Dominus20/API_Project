package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDto;

import dto.DeleteResponseDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class DeleteContactByIdRA {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
   int id;


    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com/";
        RestAssured.basePath = "api";

        ContactDto contactDto = ContactDto.builder()
                .name("Taiki")
                .lastName("Dog")
                .email("taiki@gmail.com")
                .phone("123456789")
                .address("Almaty")
                .description("dog")
                .build();

     id =  given()
                .header("Authorization",token)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);

    }

    @Test
    public void deleteContactByIdSuccess(){

        given()
                .header("Authorization",token)
                .when()
                .delete("contact/"+id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("status", containsString("Contact was deleted"));


    }


}
