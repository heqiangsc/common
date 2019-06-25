package com.jialing.swift.rmi.socket;

import java.io.IOException;

public class UserServer extends User {
    public static void main(String[] args) throws IOException {
        UserServer user = new UserServer();
        user.setAge(18);
        UserSkeleton skeleton = new UserSkeleton(user);
        skeleton.start();
    }

}
