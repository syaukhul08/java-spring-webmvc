package com.khul.webmvc.unitTest.unitTest;

import com.khul.webmvc.unitTest.unitTest.generator.SimpleDisplayNameGenerator;
import com.khul.webmvc.unitTest.Caculator;
import org.junit.jupiter.api.*;
import org.opentest4j.TestAbortedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayNameGeneration(SimpleDisplayNameGenerator.class)
class CaculatorTest {

    private Caculator calculator = new Caculator();

    @Test
    void testDivideFailed() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10,0);
        });
    }

    @Test
    void testDivideSuccess() {
        int result = calculator.divide(100,10);
        assertEquals(10,result);
        assertNotEquals(70,result);
    }

    @Test
    void testAdd() {
        int result = calculator.add(20,10);
        assertEquals(30, result);
    }

    @Test
    void testMultiple() {
        int result = calculator.multiple(10,5);
        assertNotNull(result);
        assertNotEquals(100,result);
    }

    @Test
    @Disabled
    public void testModulo(){

    }

    @BeforeEach
    public void setUp(){
        System.out.println("before each");
    }

    @AfterEach
    public void tearDown(){
        System.out.println("after each");
    }

    @BeforeAll
    public static void beforeAll(){
        System.out.println("before all");
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("after all");
    }

    @Test
    public void testAborted(){
        var profile = System.getenv("PROFILE");
        if (!"DEV".equals(profile)){
            throw new TestAbortedException("Test dibatalkan karena bukan DEV");
        }
    }

    @Test
    public void testAssumptions(){
        assumeTrue("DEV".equals(System.getenv("PROFILE")));
    }
}