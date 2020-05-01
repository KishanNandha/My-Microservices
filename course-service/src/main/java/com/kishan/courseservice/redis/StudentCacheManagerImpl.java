package com.kishan.courseservice.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.kishan.courseservice.beans.Student;

@Configuration
public class StudentCacheManagerImpl implements StudentCacheManager {

    public static final String TABLE_STUDENT = "TABLE_STUDENT";
    private RedisUtil<List<Student>> redisUtilStudent;

    @Autowired
    public StudentCacheManagerImpl(RedisUtil<List<Student>> redisUtilStudent) {
        this.redisUtilStudent = redisUtilStudent;

    }

    @Override
    public void cacheStudnetDetails(String courseName,List<Student> students){
        redisUtilStudent.putMap(TABLE_STUDENT,courseName,students);
        redisUtilStudent.setExpire(TABLE_STUDENT,1,TimeUnit.DAYS);
    }

	@Override
	public List<Student> getCachedStudentsForCourse(String courseName) {
		
		//return redisUtilStudent.getMapAsSingleEntry(TABLE_STUDENT, courseName);
		
		//This is used to temp disabled cache
		return null;
	}

}
