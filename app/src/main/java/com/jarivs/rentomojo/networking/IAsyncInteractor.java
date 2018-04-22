package com.jarivs.rentomojo.networking;

/**
 * Created by Sachin
 */
public interface IAsyncInteractor {
    void validateCredentialsAsync(OnRequestListener listener, int pid, String url);
}
