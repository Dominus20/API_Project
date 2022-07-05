package okhttp;

import com.google.gson.Gson;
import dto.AllContactDto;
import dto.ContactDto;
import dto.ErrorDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContacts {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @Test
    public void getAllContactSuccess() throws IOException {

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .get()
                .addHeader("Authorization", token)
                .build();



        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        AllContactDto allContactDto = gson.fromJson(response.body().string(), AllContactDto.class);
        allContactDto.getContacts();

        List<ContactDto> contacts = allContactDto.getContacts();
        for (ContactDto contact:contacts) {
            System.out.println(contact.toString());
            System.out.println("*****************");

        }



    }
}


