package okhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.DeleteResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactById {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    int id;

    @BeforeMethod
    public void createContact() throws IOException {
        ContactDto contact = ContactDto.builder()
                .name("Kaya")
                .lastName("First")
                .email("kkk@gmail.com")
                .phone("1234567")
                .address("Tel-Aviv")
                .description("dog")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        ContactDto contactDto = gson.fromJson(response.body().string(), ContactDto.class);
        id = contactDto.getId();
        System.out.println(id);

    }


    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/"+id)
                .delete()
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        DeleteResponseDto deleteResponseDto = gson.fromJson(response.body().string(), DeleteResponseDto.class);

        Assert.assertEquals(deleteResponseDto.getStatus(), "Contact was deleted!");

    }
}



