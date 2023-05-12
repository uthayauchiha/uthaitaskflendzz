package com.uthai.uthaitask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

        @GET("users")
        Call<ArrayList<User>> getUsers();

}