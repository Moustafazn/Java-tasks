/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer;

import com.mobiquityinc.packer.packageServices.FileParser;
import com.mobiquityinc.packer.packageServices.PackageHandler;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mzein
 */
public class PackerTest {
    
    // put the full path here
    private final String filePath = "";
    //write the expected result here
    private final String expResult = "";

    public PackerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        System.out.println("The test cases will be executed now: com.mobiquityinc.packer.PackerTest.setUp()");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of pack method, of class Packer.
     */
    @Test
    public void testPack() {
        try {
            System.out.println("pack");
            String result = Packer.pack(filePath);
            assertNotNull(result);
            assertTrue(result.length() > 0);
            System.out.print(result);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            fail("The test case  (testPack) is failed");
            Logger.getLogger(PackerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testReadFile() {
        File file;
        try {
            file = new File(filePath);
            assertNotNull(file);
            assertTrue(file.exists());
            FileParser.readFileandFillPackages(filePath);
            assertNotNull(PackageHandler.getInstance().getPackages());
            assertTrue(!PackageHandler.getInstance().getPackages().isEmpty());
        } catch (Exception ex) {
            fail("The test case  (testgetOptmialPackage) is failed");
            Logger.getLogger(PackerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testgetOptmialPackage() {
        try {
            String result = PackageHandler.getInstance().getOptimalPackage();
            assertNotNull(result);
            assertTrue(result.length() > 0);
            assertEquals(expResult, result);
        } catch (Exception ex) {
            fail("The test case  (testgetOptmialPackage) is failed");
            Logger.getLogger(PackerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
