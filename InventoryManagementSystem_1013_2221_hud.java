// 代码生成时间: 2025-10-13 22:21:34
package inventorymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * InventoryManagementSystem is a Java application that uses the MyBatis framework to manage inventory.
 * It provides methods to add, update, delete, and retrieve inventory items.
# TODO: 优化性能
 */
public class InventoryManagementSystem {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor to initialize the SqlSessionFactory.
     */
    public InventoryManagementSystem() {
        String resource = 