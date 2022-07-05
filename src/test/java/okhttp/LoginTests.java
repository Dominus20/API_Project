package okhttp;

import com.google.gson.Gson;
import dto.Auth;
import dto.AuthResponse;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests {

    static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();


    @Test
    public void loginSuccess() throws IOException {
        Auth auth = Auth.builder().email("noa@gmail.com").password("Nnoa12345$").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        AuthResponse authResponse = gson.fromJson(response.body().string(), AuthResponse.class);
        System.out.println(authResponse.getToken());
    }

    @Test
    public void loginNegativeWrongEmailFormat() throws IOException {

        Auth auth = Auth.builder().email("noagmail.com").password("Nnoa12345$").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(), "Wrong email format! Example : name@gmail.com");

    }

}
