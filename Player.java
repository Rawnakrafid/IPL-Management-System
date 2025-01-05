package org.example.javanetworking.Player;
import java.io.Serializable;
import java.math.BigDecimal;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L; // Ensure compatibility between versions of the class

    private String Name;
    private String Country;
    private int age;
    private double Height;
    private String Club;
    private String Position;
    private int number;
    private int Salary;

    public Player(Player player) {
    }

    // Getters and Setters
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public String getClub() {
        return Club;
    }

    public void setClub(String club) {
        Club = club;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public Player(String name, String country, int age, double height, String club, String position, int salary) {
        Name = name;
        Country = country;
        this.age = age;
        Height = height;
        Club = club;
        Position = position;
        Salary = salary;
        this.number = -1;
    }

    public Player(String name, String country, int age, double height, String club, String position, int number, int salary) {
        Name = name;
        Country = country;
        this.age = age;
        Height = height;
        Club = club;
        Position = position;
        this.number = number;
        Salary = salary;
    }

    public void print() {
        System.out.println("Name: " + getName());
        System.out.println("Country: " + getCountry());
        System.out.println("Age: " + getAge());
        System.out.println("Height: " + getHeight());
        System.out.println("Club: " + getClub());
        System.out.println("Position: " + getPosition());
        if (number == -1) {
            System.out.println("Jersey Number not available for the player");
        } else {
            System.out.println("Number: " + getNumber());
        }
        String formattedsalary = BigDecimal.valueOf(getSalary()).stripTrailingZeros().toPlainString();
        System.out.println("Weekly Salary: " + formattedsalary);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("\n");
        sb.append("Country: ").append(getCountry()).append("\n");
        sb.append("Age: ").append(getAge()).append("\n");
        sb.append("Height: ").append(getHeight()).append("\n");
        sb.append("Club: ").append(getClub()).append("\n");
        sb.append("Position: ").append(getPosition()).append("\n");
        if (number == -1) {
            sb.append("Jersey Number: Not available for the player").append("\n");
        } else {
            sb.append("Number: ").append(getNumber()).append("\n");
        }
        String formattedSalary = BigDecimal.valueOf(getSalary()).stripTrailingZeros().toPlainString();
        sb.append("Weekly Salary: ").append(formattedSalary).append("\n");

        return sb.toString();
    }
}
