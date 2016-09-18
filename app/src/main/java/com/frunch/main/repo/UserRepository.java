package com.frunch.main.repo;

import com.frunch.main.model.User;

/**
 * Created by MAHITH on 9/18/2016.
 */
public class UserRepository extends com.strongloop.android.loopback.UserRepository<User> {
    public interface LoginCallback  extends com.strongloop.android.loopback.UserRepository.LoginCallback<User> {
    }
    public UserRepository() {
        super("user",null,User.class);
    }
}
