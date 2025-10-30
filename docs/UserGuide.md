# User Guide

## Introduction

Uniflow is a command-line university module management assistant designed for students.
It helps you organize your timetable, track grades and GPA, add and view reviews, and rate courses — all through simple text-based commands.

## Quick Start
1. Ensure you have **Java 17 or above** installed on your computer.
2. Download the latest version of **Uniflow.jar** from your release page.
3. Open a terminal and navigate to the folder containing the JAR file.
4. Run the application with:
```commandline
java -jar Uniflow.jar
```
5. Start entering commands (see the Command Summary below for examples).

## Features 

{Give detailed description of each feature}

### Adding a Module: `insert`
Adds a new module to your timetable.

Format:
```commandline
insert i/MODULE_CODE n/NAME d/DAY f/START_TIME t/END_TIME s/SESSION_TYPE
```
Example:
```commandline
insert i/CS2113 n/Software Engineering d/Monday f/14:00 t/16:00 s/Lecture
```

### Deleting a Module - `delete`
Removes module by ID.
Format:
```commandline
delete i/MODULE_CODE
```
Example:
```commandline
delete i/CS2113
```

### List All Module - `list`
Displays all modules in your timetable.
Format:
```commandline
list
```

### Filter Modules - `filter`
Filters modules by day, session, type, or tutorial presence.
Example:
```commandline
filter day/Monday
filter type/tutorial
filter hastutorial
```

### Rate a Course - `rate`
Allows users to add or view numerical ratings for a course.
Rating Format:
```commandline
rate COURSE_CODE RATING
```
Example:
```commandline
rate CS2113 4
```
View Average Rating Format:
```commandline
rate CS2113
```
### Add a Course's Grade Information - `addgrade`
Allows users to add the grade of a specific course. It will be stored in the course record (or grade record).  
The information stored will be the course code, number of credits (or units), grade attained and whether it is a major required course.  
  
**IS_MAJOR**: true if the course is a major required course, false if not.  
  
Format:
```commandline
addgrade c/COURSE_CODE cr/NUMBER_OF_CREDITS g/GRADE m/IS_MAJOR
```
Example:
```commandline
addgrade c/CS2113 cr/4 g/A m/true
```
In this example, you added the course CS2113 to the record. It is a 4-credit course. You got an A in the course. It is a major required course. 

### Add a Course's Predicted Grade - `addtestgrade`
Allows users to add a predicted grade of a specific course they are (or will be) studying.  
It will only be stored in a temporary record and will not be saved.  
The information stored will be the course code, number of credits (or units), grade attained and whether it is a major required course.  
  
*It is used for testing their projected GPA.*  
  
Format:
```commandline
addtestgrade c/COURSE_CODE cr/NUMBER_OF_CREDITS g/GRADE m/IS_MAJOR
```
Example:
```commandline
addtestgrade c/CS2113 cr/4 g/A m/true
```
In this example, you added the course CS2113 to the temporary record. It is a 4-credit course. You assume that you get an A in the course. It is a major required course. 

### Compute Cumulative GPA - `gpa`
Allows users to get a summary of their academic performance based on the current course record.   
It will show the cumulative GPA and major GPA, respectively.  
  
Note: Major GPA indicates the cumulative GPA for their major-required courses.  
*- Many universities include this as a graduation requirement*  
*- Even the user's school does not count their major GPA separately. Major GPA here allows users to have an idea of their performance in the required courses of their major.*
  
Format:
```commandline
gpa
```
Example:  
  
Suppose you have added two courses:  
*CS2113, 5 credits, grade: B, major course*  
*EC3322, 5 credits, grade: A, not a major required course*
  
Expected output:
```commandline
____________________________________________________________  
____________________________________________________________  
 You have studied 2 courses.  
 Your total grade points are 42.5  
 Number of credits you have studied: 10  
 Your GPA is: 4.25  
____________________________________________________________  
____________________________________________________________  
 You have studied 1 major courses.  
 Your total major course grade points are 17.5  
 Number of credits you have studied: 5  
 Your major GPA is: 3.50  
____________________________________________________________  
____________________________________________________________
```

### Compute a projected GPA - `projectgpa`
Allows users to compute their **predicted** GPA and major course GPA, based on their predicted grades of courses they are studying. 
    
Format:
```commandline
projectgpa
```
Example:  
  
Suppose you have two courses in your saved records:  
*CS2113, 5 credits, grade: B, major course*  
*EC3322, 5 credits, grade: A, not a major required course*  
  
You are studying a course in the upcoming semester:  
*EC3303, 5 credits, your prediction for your grade: A, major course*  
  
And you added it to the temporary record.  

Expected output:
```commandline
____________________________________________________________
____________________________________________________________
 Your projected GPA will be: 4.50
 Your projected major GPA will be: 4.25
____________________________________________________________
____________________________________________________________
```
*Note: after the computation of the projected gpa, the newly added course will **NOT** be stored in our saved record.*

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
