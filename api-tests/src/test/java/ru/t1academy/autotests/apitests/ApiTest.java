package ru.t1academy.autotests.apitests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ApiTest {
    @Test
    public void test1() {
        System.out.println("api test 1");
    }
    @Tag("smoke")
    @Test
    public void test2() {
        System.out.println("smoke api test 2");
    }
}
