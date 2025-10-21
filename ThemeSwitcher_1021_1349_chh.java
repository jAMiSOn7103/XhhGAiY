// 代码生成时间: 2025-10-21 13:49:23
package com.example.theme;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;

/**
 * ThemeSwitcher provides functionality to switch between different themes.
 */
public class ThemeSwitcher {

    private SqlSessionFactory sqlSessionFactory;

    public ThemeSwitcher(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Retrieves a list of available themes.
     *
     * @param session The MyBatis session.
     * @return A list of theme names.
     */
    public List<String> getAvailableThemes(SqlSession session) {
        try {
            return session.selectList("com.example.theme.ThemeMapper.getThemes");
        } catch (Exception e) {
            // Handle any SQL errors or mapper issues
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    /**
     * Switches the current theme to the specified theme name.
     *
     * @param session The MyBatis session.
     * @param themeName The name of the theme to switch to.
     * @return True if the theme switch was successful, false otherwise.
     */
    public boolean switchTheme(SqlSession session, String themeName) {
        try {
            session.update("com.example.theme.ThemeMapper.switchTheme", themeName);
            return true;
        } catch (Exception e) {
            // Handle any SQL errors or mapper issues
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
}
