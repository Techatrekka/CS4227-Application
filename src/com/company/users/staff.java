package com.company.users;

interface staff extends user{
    public void orderStock(Date date, String order);
    public void takeStock(Date date, String stock, int id_num);
    public void takePayment();
}