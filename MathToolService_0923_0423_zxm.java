// 代码生成时间: 2025-09-23 04:23:41
package com.example.mathtool;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.InputStream;
import java.util.Scanner;

public class MathToolService {
    private SqlSessionFactory sqlSessionFactory;
    private Scanner scanner;
    
    public MathToolService() {
        try {
            // Load MyBatis configuration file
            InputStream inputStream = MathToolService.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            scanner = new Scanner(System.in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Calculate the sum of two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The sum of a and b.
     */
    public double add(double a, double b) {
        return a + b;
    }
    
    /**
     * Calculate the difference between two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The difference between a and b.
     */
    public double subtract(double a, double b) {
        return a - b;
    }
    
    /**
     * Calculate the product of two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The product of a and b.
     */
    public double multiply(double a, double b) {
        return a * b;
    }
    
    /**
     * Calculate the quotient of two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The quotient of a and b.
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
    
    /**
     * Main method to demonstrate the usage of MathToolService.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        MathToolService service = new MathToolService();
        try {
            System.out.println("Enter the first number: ");
            double a = service.scanner.nextDouble();
            System.out.println("Enter the second number: ");
            double b = service.scanner.nextDouble();
            
            System.out.println("Sum: " + service.add(a, b));
            System.out.println("Difference: " + service.subtract(a, b));
            System.out.println("Product: " + service.multiply(a, b));
            System.out.println("Quotient: " + service.divide(a, b));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.scanner.close();
        }
    }
}