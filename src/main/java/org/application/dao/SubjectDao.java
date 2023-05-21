package org.application.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectDao {
    @Autowired
    private DataSource dataSource;

    private List<String> dayOfWeekList = new ArrayList<>();

//    @Autowired
//    private SubjectDao(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }


    public List<String> getDayOfWeekFromSubjectCount(int count) {
        try (Connection connection = dataSource.getConnection()) {
            dayOfWeekList.clear();
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT day
                    FROM Subjects
                    GROUP BY day
                    HAVING count(name)=?
                    """);

            preparedStatement.setInt(1, count);

            ResultSet resultSet = preparedStatement.executeQuery();
            //List<String> dayOfWeek = new ArrayList<>();
            while (resultSet.next()) {
                dayOfWeekList.add(resultSet.getString("day"));
            }
            return dayOfWeekList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDayCountsClassroom(int count){
        try (Connection connection = dataSource.getConnection()) {
            dayOfWeekList.clear();
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT day
                                        FROM Subjects
                                        GROUP BY day
                                        HAVING count(classroom)=?
                    """);


            preparedStatement.setInt(1, count);

            ResultSet resultSet = preparedStatement.executeQuery();
            //List<String> dayOfWeek = new ArrayList<>();
            while (resultSet.next()) {
                dayOfWeekList.add(resultSet.getString("day"));
            }
            return dayOfWeekList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
