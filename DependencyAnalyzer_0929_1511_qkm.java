// 代码生成时间: 2025-09-29 15:11:18
package com.example.myapp;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Dependency Analyzer class that analyzes dependencies using MyBatis.
 */
public class DependencyAnalyzer {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor that initializes the SqlSessionFactory.
     */
    public DependencyAnalyzer() {
        String resource = "mybatis-config.xml";
        try {
            Reader reader = Resources.getResourceAsReader(resource);
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyzes dependencies using the MyBatis mapper.
     * @return List of dependencies.
     */
    public List<Dependency> analyzeDependencies() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DependencyMapper mapper = session.getMapper(DependencyMapper.class);
            return mapper.listDependencies();
        }
    }

    /**
     * Main method to run the DependencyAnalyzer.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        DependencyAnalyzer analyzer = new DependencyAnalyzer();
        List<Dependency> dependencies = analyzer.analyzeDependencies();
        for (Dependency dependency : dependencies) {
            System.out.println(dependency);
        }
    }
}

interface DependencyMapper {
    List<Dependency> listDependencies();
}

class Dependency {
    private String groupId;
    private String artifactId;
    private String version;
    private List<Dependency> children;

    // Getters and setters
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
    public String getArtifactId() { return artifactId; }
    public void setArtifactId(String artifactId) { this.artifactId = artifactId; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public List<Dependency> getChildren() { return children; }
    public void setChildren(List<Dependency> children) { this.children = children; }

    @Override
    public String toString() {
        return "Dependency{" +
                "groupId='" + groupId + '"' +
                ", artifactId='" + artifactId + '"' +
                ", version='" + version + '"' +
                '}';
    }
}
