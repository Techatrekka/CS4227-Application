package com.company.menu;

import java.util.ArrayList;

abstract class menu{
    ArrayList<MenuItem>menuList;
    int menuID;
    String name;


    public int getMenuID(){
        return 0;

    }
    public void setMenuID(int id){

    }
    public String getName(){
        return null;

    }
    public void setName(String name){

    }
    public ArrayList<MenuItem> getMenuItems(){
        return menuList;

    }
    public void setMenuItems(ArrayList<MenuItem> items){
        this.menuList = items;
    }
    public void printMenu(){
        for(int x=0; x<menuList.size();x++){
            System.out.println(menuList.get(x).getName());
        }
    }
}
