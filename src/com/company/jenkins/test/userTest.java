package com.company.jenkins.test;

import com.company.users.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class userTest {
    String name = "test name";
    String email = "email@email.com";
    String userType = "Customer";
    int ID = 0001;
    Customer testUser = new Customer();
    @Test
    public void testName(){
        testUser.setFullName(name);
        assertEquals(testUser.getFullName(), name);
    }
    @Test
    public void testEmail(){
        testUser.setEmail(email);
        assertEquals(email, testUser.getEmail());
    }
    @Test
    public void testID(){
        testUser.setIdNum(ID);
        assertEquals(ID, testUser.getIdNum());
    }
    @Test
    public void testUsetType(){
        testUser.setUserType(userType);
        assertEquals(userType, testUser.getUserType());
    }
}