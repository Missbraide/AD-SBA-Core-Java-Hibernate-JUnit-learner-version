//package sba.sms.services;
//
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//import org.junit.jupiter.api.*;
//import sba.sms.models.Course;
//import sba.sms.utils.CommandLine;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@FieldDefaults(level = AccessLevel.PRIVATE)
//class CourseServiceTest {
//    static CourseService courseService;
//    @BeforeAll
//    static void beforeAll() {
//        courseService = new CourseService();
//        CommandLine.addData();
//    }
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    @Order(1)
//    void getAllCourses() {
//
//        List<Course> expected = new ArrayList<>(Arrays.asList(
//                new Course(1,"Java", "Phillip Witkin"),
//                new Course(2,"Frontend", "Kasper Kain"),
//                new Course(3,"JPA", "Jafer Alhaboubi"),
//                new Course(4,"Spring Framework", "Phillip Witkin"),
//                new Course(5,"SQL", "Phillip Witkin")
//        ));
//        assertThat(courseService.getAllCourses()).hasSameElementsAs(expected);
//    }
//}
//
//
//

package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import sba.sms.models.Course;
import sba.sms.utils.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class CourseServiceTest {
    static CourseService courseService;
    @BeforeAll
    static void beforeAll() {
        courseService = new CourseService();
        CommandLine.addData();
    }
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    @Order(4)
    void getCourseByWrongId() {
        // Negative test
        Course actual = courseService.getCourseById(999);
        assertThat(actual).isInstanceOf(Course.class);
        assertThat(actual.getName()).isNull();
        assertThat(actual.getInstructor()).isNull();
        assertThat(actual.getId()).isZero();
    }


}