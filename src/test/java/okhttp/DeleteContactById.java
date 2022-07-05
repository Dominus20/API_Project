package okhttp;

import com.google.gson.Gson;
import dto.DeleteResponseDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactById {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @BeforeMethod
    public void createContact(){

    }


    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/231")
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



