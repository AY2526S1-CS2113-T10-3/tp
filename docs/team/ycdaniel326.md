# Chiu Yin Chi - Project Portfolio Page  
  
## Overview
**Uniflow** is a command-line university students management assistant that helps students organize their timetables, track academic progress, and collaborate through course reviews and ratings.
It allows users to **add modules, detect timetable clashes, record grades, calculate GPA, add reviews,** and **rate courses** using intuitive text commands.

This product is designed for university students who prefer a fast, keyboard-based workflow and need a compact way to manage academic and timetable data without relying on GUI-based tools.

## Summary of Contributions

### GPA calculator
  
- Allows users to store their completed course's grade information in a record.  
  Users can indicate whether the course is a major-required course.
    
  Related command: `addgrade`  
  Related codes in the project: `Course`, `CourseRecord`, `AddGradeCommand`  
- Added a separate list that stores the record of major required courses specifically.  
  *Note: Users can indicate whether a course is a major-required course when they add the course.*
    
  Related codes: `Course`, `CourseRecord`  
- The record is saved in a text file and can be reused in the future.
    
  Related codes in the project: `GradeStorage`

- Allows users to compute their cumulative GPA and major-specific GPA respectively, based on their completed course record
      
  Related command: `gpa`  
  Related codes in the project: `CourseRecord`, `ComputeGpaCommand`  
- Allows users to enter their predicted grades for not yet completed courses.  
  They can use it to compute a projected GPA based on their predictions.
  The added courses will only be stored temporarily, but will not affect their current real record of grades.  
  This function allows users to set potential strategies for courses they are going to complete.
    
  Related command: `addtestgrade`, `projectgpa`  
  Related codes in the project: `CourseRecord`, `AddTestGradeCommand`
    
- `Parser`, `Uniflow`: amended codes in `Parser` and `Uniflow` for executing the commands for GPA calculator.
- Added JUnit tests for some of the functions.
