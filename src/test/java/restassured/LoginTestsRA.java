package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.Auth;
import dto.AuthResponse;
import dto.ErrorDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;


public class LoginTestsRA {

    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com/";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginSuccess(){
        Auth auth = Auth.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345$")
                .build();

        AuthResponse authResponse = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponse.class);
        System.out.println(authResponse.getToken());


    }

//    @Test
//    public void loginWithWrongEmailFormat(){
//        Auth auth = Auth.builder()
//                .email("noagmail.com")
//                .password("Nnoa12345$")
//                .build();
//
//      ErrorDto errorDto = given()
//                .body(auth)
//                .contentType(ContentType.JSON)
//                .when()
//                .post("login")
//                .then()
//                .assertThat().statusCode(400)
//                .extract().response().as(ErrorDto.class);
//
//        Assert.assertEquals(errorDto.getMessage(), "Wrong email format! Example: noa@mail.com");
//    }

    @Test
    public void  loginWithWrongEmailFormat(){
        Auth auth = Auth.builder()
                .email("noagmail.com")
                .password("Nnoa12345$")
                .build();

        ErrorDto errorDto = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDto.class);

        Assert.assertEquals(errorDto.getMessage(),"Wrong email format! Example: name@mail.com");
    }

    @Test
    public void loginWrongEmailAssertRA(){

        given()
                .body(Auth.builder().email("noagmail.com").password("Nnoa12345$").build())
                .contentType(ContentType.JSON)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("Wrong email format! Example: name@mail.com"))
                .assertThat().body("details", containsString("uri=/api/login"));

    }

}
