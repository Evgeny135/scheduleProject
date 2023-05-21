package org.application.dao;

import org.application.models.DayOfWeek;
import org.application.models.Subject;
import org.application.models.Teacher;
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
public class ScheduleDao {
    @Autowired
    private DataSource dataSource;

    public List<Teacher> index(){
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT t.id,t.name, lessons.count_students,lessons.hours_per_week,s.id AS subid,s.name AS subname,classroom, s.day
            FROM lessons INNER JOIN subjects s on s.id = lessons.subject INNER JOIN teachers t on lessons.teacher = t.id
            """);

            ResultSet resultSet = preparedStatement.executeQuery();
            Teacher teacher = null;
            List<Teacher> teacherList = new ArrayList<>();
            while (resultSet.next()){
                int teacherId = resultSet.getInt("id");
                String teacherName =  resultSet.getString("name");
                int subjectId = resultSet.getInt("subid");
                String subjectName = resultSet.getString("subname");
                int subjectClassRoom = resultSet.getInt("classroom");
                int hoursPerWeek  = resultSet.getInt("hours_per_week");
                int countStudent = resultSet.getInt("count_students");
                DayOfWeek dayOfWeek = DayOfWeek.getDayByName(resultSet.getString("day"));
                Subject subject = new Subject(subjectId, subjectName, dayOfWeek, subjectClassRoom);
                teacher = new Teacher(teacherId, teacherName, subject,
                        hoursPerWeek,countStudent);
                teacherList.add(teacher);
            }
            return teacherList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void replaceSubjectsFromDayOfWeek(String name, DayOfWeek day, DayOfWeek dayOfWeek){
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("""
        UPDATE subjects SET
                            day = ?
        WHERE name = ? AND day = ?;
""");
            preparedStatement.setString(1,dayOfWeek.getName());
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,day.getName());
            int i = preparedStatement.executeUpdate();
            if (i ==0){
                System.out.println("Предмета не найдено или он уже на заданном дне недели");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<DayOfWeek> getDays() {
        List<DayOfWeek> dayOfWeekList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                            SELECT distinct day
                            FROM lessons l JOIN subjects s on l.subject = s.id;
                    """);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dayOfWeekList.add(DayOfWeek.getDayByName(resultSet.getString("day")));
            }
            return dayOfWeekList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getName(){
        List<String> nameSubjects = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                            SELECT distinct name
                            FROM lessons l JOIN subjects s on l.subject = s.id;
                    """);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nameSubjects.add(resultSet.getString("name"));
            }
            return nameSubjects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
