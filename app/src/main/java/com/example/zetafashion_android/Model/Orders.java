package com.example.zetafashion_android.Model;

public class Orders {
    String Name,Email,Phone,Address;

    public Orders() {
    }

    public Orders(String name, String email, String phone, String address) {
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
