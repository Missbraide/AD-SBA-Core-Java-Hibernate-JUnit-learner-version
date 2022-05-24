package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Log
public class StudentService implements StudentI {
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            students = s.createQuery("from student", Student.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }


    @Override
//    public void createStudent(Student student) {
//        Session s = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        try {
//            tx = s.beginTransaction();
//                s.persist(student);
//                tx.commit();
//        }
//            catch(HibernateException exception){
//                if (tx != null) tx.rollback();
//                exception.printStackTrace();
//            } finally {
//            s.close();
//        }
//        }

    public void createStudent(Student student) {
        Transaction tx = null;
        student.setEmail(student.getEmail().toLowerCase(Locale.ROOT));
        student.setName(student.getName().toLowerCase(Locale.ROOT));
        try (Session s = HibernateUtil.getSessionFactory().openSession()){
            List<Student> allStudents = getAllStudents();
            tx = s.beginTransaction();
            if (!allStudents.contains(student)) {
                s.persist(student);
                tx.commit();
            } else{
                System.out.printf("Student Exist!");
            }
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        }
    }

    @Override
    public Student getStudentByEmail(String email){
        Student student = null;
        try  (Session s = HibernateUtil.getSessionFactory().openSession()) {
            student = s.get(Student.class, email);
            if(student == null){
                System.out.println("Student not found!");
            }
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student student = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()){
            student = s.get(Student.class, email);
            if (student == null) {
                System.out.println("Student not found!");
                return true;
            }
            return student.getPassword().equals(password);
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()){
            tx = s.beginTransaction();
            Student student = s.get(Student.class, email);
            Course course = s.get(Course.class, courseId);
            if (student.getCourses().contains(course)) {
                System.out.printf( student.getName(), "already registered for", course.getName());
                return;
            }
            student.addCourse(course);
            System.out.printf(student.getName(), "Successfully registered for",  course.getName());
            s.merge(student);
            tx.commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        List<Course> coursesList = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()){
            NativeQuery q = s.createNativeQuery("SELECT c.id, c.name, c.instructor FROM Course as c JOIN student_courses as sc ON sc.courses_id = c.id JOIN Student as s ON s.email = sc.student_email WHERE s.email = :email",Course.class);
            q.setParameter("email",email);
            coursesList = q.getResultList();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
        return coursesList;
    }

    }








