package sba.sms.services;

import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;
public class CourseService implements CourseI {
    @Override
    public void createCourse(Course course) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()){
            tx = s.beginTransaction();
            s.persist(course);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        }
    }
    @Override
    public Course getCourseById(int courseId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Course course = s.get(Course.class, courseId);
            if(course == null)
                throw new HibernateException("Course not found!");
            else return course;
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
        return new Course();
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            allCourses = s.createQuery("From Course", Course.class).getResultList();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
        return allCourses;
    }

}

