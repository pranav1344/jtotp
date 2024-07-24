/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package io.github.pranav1344.totp.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pranav
 */
public class Base32Test {
    
    public Base32Test() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of encode method, of class Base32.
     */
    @Test
    public void testEncode() {
        System.out.println("encode");
        byte[] bytes = "1234567890".getBytes();
        String expResult = "GEZDGNBVGY3TQOJQ";
        String result = Base32.encode(bytes);
        assertEquals(expResult, result);
    }

    /**
     * Test of decode method, of class Base32.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        String base32 = "GEZDGNBVGY3TQOJQ";
        byte[] expResult = "1234567890".getBytes();
        byte[] result = Base32.decode(base32);
        assertArrayEquals(expResult, result);
    }
    
}
