package com.company.users;

public abstract class User {
    private String userType;
    private int idNum;
    private String fullName;
    private String email;

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

    public int getIdNum() {
        return idNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void placeOrder(int userId /**, Order order**/){
        // code
    }

    public void cancelOrder(int userId, int orderId){
        // code
    }
}
