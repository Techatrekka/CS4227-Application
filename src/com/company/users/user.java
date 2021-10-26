package com.company.users;

interface user {
    public void logon(String username, String password);
    public void Customer(int id_num, String full_name, String email);
    public void placeOrder(int order_id, String order);
    public void editOrder(int order_id, String new_order);
    public void cancelOrder(int order_id);
    public void redeemVoucher(int order_id, int voucher_number);
    public void redeemCoupon(int order_id, int coupon_number);
    public void viewMenu(int menu_id);
    public void payOrder(int order_id);
    public void addLoyaltyProgramme(int id_num);
}
