package org.example.javanetworking;

import java.io.Serializable;

public class TestObject implements Serializable {
    private String message;

    public TestObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TestObject{message='" + message + "'}";
    }
}
