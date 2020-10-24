package com.kupug.kuoauth.utils;

import org.junit.Assert;
import org.junit.Test;

public class UuidUtilsTest {

    @Test
    public void getUUID() {
        String uuid = UuidUtils.getUUID();

        System.out.println(uuid);

        Assert.assertNotNull("UuidUtilsTest.getUUID", uuid);
    }
}