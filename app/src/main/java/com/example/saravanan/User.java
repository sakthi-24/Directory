package com.example.saravanan;

public class User {
    String Name = null;
    String Email = null;
    String Department = null;
    String Batch = null;
    String Country =null;
    String Image=null;


    public User(String Sfname, String Semail,String Sdepartment,String Sbatch,String Scountry,String simage) {

        super();

        this.Name = Sfname;
        this.Email = Semail;
        this.Department = Sdepartment;
        this.Batch=Sbatch;
        this.Country=Scountry;
        this.Image=simage;
    }

    public String getName() {

        return Name;

    }
    public void setName(String name) {

        this.Name = name;

    }


    public String getEmail() {

        return Email;

    }
    public void setEmail(String email) {

        this.Email = email;

    }

    public String getDepartment()
    {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }


    public String getImage()
    {
        return Image;
    }

    public void setImage(String image)
    {
        Image = image;
    }

    @Override
    public String toString() {

        return  Name + " " + Email+ " " + Department +" "  + Batch+ " " + Country + " "+ Image ;

    }
}
