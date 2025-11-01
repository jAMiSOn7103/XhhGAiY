// 代码生成时间: 2025-11-02 07:51:45
package com.example.packagemanager;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import java.io.Reader;
import java.util.List;

public class SoftwarePackageManager {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor that initializes the SqlSessionFactory.
     */
    public SoftwarePackageManager() {
        try {
            String resource = "mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlSessionFactory", e);
        }
    }

    /**
     * Adds a new software package to the database.
     *
     * @param packageName The name of the package to add.
     * @param version The version of the package.
     */
    public void addPackage(String packageName, String version) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("com.example.packagemanager.mapper.PackageMapper.insertPackage", new Package(packageName, version));
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error adding package: " + packageName, e);
        }
    }

    /**
     * Removes a software package from the database.
     *
     * @param packageName The name of the package to remove.
     */
    public void removePackage(String packageName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("com.example.packagemanager.mapper.PackageMapper.deletePackage", packageName);
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error removing package: " + packageName, e);
        }
    }

    /**
     * Lists all software packages in the database.
     *
     * @return A list of all packages.
     */
    public List<Package> listPackages() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("com.example.packagemanager.mapper.PackageMapper.selectAllPackages");
        } catch (Exception e) {
            throw new RuntimeException("Error listing packages", e);
        }
    }
}

// Package.java
package com.example.packagemanager;

public class Package {
    private String name;
    private String version;

    public Package() {
    }

    public Package(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

// mybatis-config.xml, PackageMapper.xml, and other MyBatis configuration files should also be created to complete the implementation.
