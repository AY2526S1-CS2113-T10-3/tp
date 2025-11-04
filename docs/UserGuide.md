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
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Got it! I've added this module:
  Module[ID=CS2113, Name=Software Engineering, Type=lecture, Day=Monday, Time=14:00-16:00]
Now you have 1 module(s) in the list.
___________________________________________________________________________
___________________________________________________________________________
```
Note: The `SESSION_TYPE` field is optional. If left empty, it will default to `Lecture`.

### Deleting a Module: `delete`
Removes a module from the timetable by index.

Format:
```commandline
delete index/MODULE_INDEX
```
Example:
```commandline
delete index/1
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Noted. I've removed this module:
  Module[ID=CS2113, Name=Software Engineering, Type=lecture, Day=Monday, Time=14:00-16:00]
Now you have 0 module(s) left in the list.
___________________________________________________________________________
___________________________________________________________________________
```

### Listing All Modules: `list`
Displays all modules in your timetable.

Format:
```commandline
list
```
Expected Output:
```commandline
___________________________________________________________________________
Your modules:
1. CS2113
2. CS3241
___________________________________________________________________________
```

### Showing Your Timetable: `show timetable`
Displays all modules in your timetable with their full details.

Format:
```commandline
show timetable
```
Expected Output:
```commandline
___________________________________________________________________________
 Here is your timetable:
 1. Module[ID=CS2113, Name=Software Engineering, Type=lecture, Day=Monday, Time=14:00-16:00]
 2. Module[ID=CS3241, Name=Computer Graphics, Type=lecture, Day=Wednesday, Time=10:00-12:00]
___________________________________________________________________________
```

### Resetting Your Timetable: `reset timetable`
Clears all modules from your timetable.

Format:
```commandline
reset timetable
```
Expected Output:
```commandline
___________________________________________________________________________
 Timetable has been reset!
___________________________________________________________________________
```
Note: This action cannot be undone. The system will confirm if your timetable is already empty.

### Filtering Modules: `filter`
Filters modules by various criteria including day, session type, module CODE, module name, or tutorial presence.

Format:
```commandline
filter day/DAY
filter type/SESSION_TYPE
filter id/MODULE_CODE
filter name/MODULE_NAME
filter hastutorial
filter notutorial
```
Examples:
```commandline
filter day/Monday
filter type/tutorial
filter hastutorial
filter notutorial
filter id/CS2113
filter name/Software
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Found 1 module(s) matching day 'Monday':
 1. Module[ID=CS2113, Name=Software Engineering, Type=lecture, Day=Monday, Time=14:00-16:00]
___________________________________________________________________________
___________________________________________________________________________
```

### Recording Component Scores: `score`
Allows users to record or view score breakdowns for a specific module (e.g., exam scores, project scores, participation).

Add Score Breakdown Format:
```commandline
score MODULE_CODE component1:value1 component2:value2
```
Example:
```commandline
score CS2113 exam:50 project:30 participation:20
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Saved score breakdown for {CS2113:{exam=50, participation=20, project=30}}
___________________________________________________________________________
___________________________________________________________________________
```

View Score Breakdown Format:
```commandline
score MODULE_CODE
```
Example:
```commandline
score CS2113
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Score breakdown for CS2113: {exam=50, participation=20, project=30}
___________________________________________________________________________
___________________________________________________________________________
```
Note: The module must already exist in your timetable before you can add scores.

### Adding a Review: `addreview`
Allows users to add a text review for a module. **This review is added to the current session in such as like in-memory and is not automatically saved to the file.**

Format:
```commandline
addreview c/MODULE_CODE u/USERNAME r/REVIEW_TEXT
```
Example:
```commandline
addreview c/CS2113 u/John r/Great course with practical projects!
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Review added for CS2113.
___________________________________________________________________________
___________________________________________________________________________
```

### Viewing Reviews: `review`
Displays all reviews for a specific module from the current session (in-memory).

Format:
```commandline
review MODULE_CODE
```
Example:
```commandline
review CS2113
```
Expected Output:
```commandline
___________________________________________________________________________
 Reviews for CS2113:
 - John: Great module with lots of practical examples
___________________________________________________________________________
```

### Editing a Review: `editreview`
Allows users to edit their existing review for a module in the current session (in-memory).

Format:
```commandline
editreview c/MODULE_CODE u/USERNAME r/NEW_REVIEW_TEXT
```
Example:
```commandline
editreview c/CS2113 u/John r/Excellent course with hands-on learning!
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Review updated for CS2113.
___________________________________________________________________________
___________________________________________________________________________
```
Note: You can only edit your own review. The username must match the one used when adding the review.

### Deleting a Review: `deletereview`
Allows users to delete their review for a module from the current session (in-memory).

Format:
```commandline
deletereview c/MODULE_CODE u/USERNAME
```
Example:
```commandline
deletereview c/CS2113 u/John
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Review deleted for CS2113.
___________________________________________________________________________
___________________________________________________________________________
```
Note: You can only delete your own review.

### Finding Reviews: `findreview`
Searches for reviews in the current session (in-memory) based on the module, the user, or both.

Formats:
```commandline
findreview c/MODULE_CODE
findreview u/USERNAME
findreview c/MODULE_CODE u/USERNAME
```
Examples:
```commandline
findreview c/CS2113
findreview u/john
findreview c/CS2113 u/john
```
Expected Output: 
```commandline
___________________________________________________________________________
 Reviews by john in CS2113:
 - CS2113 - john: Great course with practical projects!
___________________________________________________________________________
```

### Rating a Course: `rate`
Allows users to add or view numerical ratings for a module.

Rating Format:
```commandline
rate MODULE_CODE RATING
```
Example:
```commandline
rate CS2113 4
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Added Rating: 4 to CS2113
___________________________________________________________________________
___________________________________________________________________________

```
View Average Rating Format:
```commandline
rate CS2113
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 CS2113 Rating: 4.0 (1 rating(s))
___________________________________________________________________________
___________________________________________________________________________
```
### GPA calculator
Overview:  
1. It will allow you to store the grades of your completed courses in a record.  
   You can then compute the cumulative GPA and the major required courses GPA, respectively.
2. It will allow you to enter predicted grades of not yet completed courses and store them in a temporary record.  
   You can then compute a projected cumulative GPA and major GPA based on the predicted grades and saved courses' grades. 

### Adding a Course's Grade Information: `addgrade`
Allows users to add the grade of a specific course. It will be saved in a permanent course record (or grade record).  
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
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 OK! Now I have added your record of the course CS2113 (4 credits, Grade: A) (Major course)
___________________________________________________________________________
___________________________________________________________________________
```
In this example, you added the course CS2113 to the record. It is a 4-credit course. You got an A in the course. It is a major required course. 

### Adding a Course's Predicted Grade: `addtestgrade`
Allows users to add a predicted grade of a specific course they are (or will be) studying.  
It will only be stored in a temporary record and will not be saved.  
The information stored will be the course code, number of credits (or units), the predicted grade the user might attain and whether it is a major required course.  
  
*It is used for testing their projected GPA.*  
  
Format:
```commandline
addtestgrade c/COURSE_CODE cr/NUMBER_OF_CREDITS g/GRADE m/IS_MAJOR
```
Example:
```commandline
addtestgrade c/CS2113 cr/4 g/A m/true
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Added course: CS2113 (4 credits, Grade: A) (Major course) in temporary record.
___________________________________________________________________________
___________________________________________________________________________
```
In this example, you added the course CS2113 to the temporary record. It is a 4-credit course. You assume that you get an A in the course. It is a major required course. 

### Listing out the currently stored course grades: `showgrade`
Displays what courses are saved in the permanent record.  
Allows users to keep track of what courses they have saved in the permanent record.  
  
Format:  
```commandline
showgrade
```
Expected Output:
```commandline
___________________________________________________________________________
___________________________________________________________________________
Currently, you have 3 saved course grades
Here are your saved course grade records:
___________________________________________________________________________
1. EC3322 (5 credits, Grade: B) (Major course)
2. CS2113 (5 credits, Grade: A) (Major course)
3. CS1010 (4 credits, Grade: A) (Major course)
___________________________________________________________________________
___________________________________________________________________________
```

### Listing out the temporarily stored predicted grades: `showtestgrade`
Displays what courses are currently in the temporary record.  
Allows users to keep track of what courses they have input into the temporary record.  
  
Format:
```commandline
showtestgrade
```
The output is similar to `showgrade`
### Removing a Course From Saved Record: `removegrade`
Allows users to remove the record of a specific course. It will be deleted from the permanent course record (or grade record).  
This is to allow users to modify the record in case of inputting the details wrongly.  

Format:  
```commandline
removegrade INDEX
```
Example:  
```commandline
removegrade 3
```
Assume you have three saved courses
```commandline
1. EC3322 (5 credits, Grade: B) (Major course)
2. CS2113 (5 credits, Grade: A) (Major course)
3. CS1010 (4 credits, Grade: A) (Major course)
```
After `removegrade 3`, the 3rd course in the list will be removed.  
  
Expected Output:  
```commandline
___________________________________________________________________________
___________________________________________________________________________
 Removed course grade record: CS1010 (4 credits, Grade: A) (Major course)
___________________________________________________________________________
___________________________________________________________________________
```
Your saved record will only have two courses left. That is:  
```commandline
1. EC3322 (5 credits, Grade: B) (Major course)
2. CS2113 (5 credits, Grade: A) (Major course)
```

### Removing a Course From Temporary Record: `removetestgrade`
Allows users to remove a specific course from the temporary record. (That you use to project your GPA)  
This is to allow users to modify the temporary record in case of inputting the details wrongly or hoping to change the details.  

Format:  
```commandline
removetestgrade INDEX
```
Example:  
```commandline
removetestgrade 3
```
Similar to `removegrade`, after `removetestgrade 3`, the 3rd course in the temporary record will be removed.  
The output and effect is similar to `removegrade`

### Computing Cumulative GPA: `gpa`
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
  
Expected Output:
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

### Computing a projected GPA: `projectgpa`
Allows users to compute their **predicted** GPA and major course GPA, based on their predicted grades of courses they are/will be studying. 
    
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

Expected Output:
```commandline
____________________________________________________________
____________________________________________________________
 Your projected GPA will be: 4.50
 Your projected major GPA will be: 4.25
____________________________________________________________
____________________________________________________________
```
*Note: after the computation of the projected GPA, the newly added course will **NOT** be stored in our saved record.*  
*Only completed courses will be stored in the permanent record.*

### Counting Reviews: `amount reviews`
Counts the number of reviews **currently in memory** for a specific module or user.

Format:
```commandline
amount reviews [c/MODULE_CODE] [u/USERNAME]
```
Example:
```commandline
amount reviews c/CS2113
```
Expected Output:
```commandline
___________________________________________________________________________
Course CS2113 has 1 review(s).
___________________________________________________________________________
```

### Loading Reviews from File: `load reviews`
Clears all reviews from memory and reloads them from the data/reviews.txt file.
Warning: If you have unsaved reviews in memory, this command will ask if you want to merge them into the file before reloading.

Format:
```commandline
load reviews
```
Expected Output
```commandline
___________________________________________________________________________
Reloading all reviews from file...
There are unsaved reviews in memory. Do you want to add them to the database? (yes/no)
```
Followed by:
```commandline
All reviews successfully reloaded from file. (Memory now matches file)
___________________________________________________________________________
```

### Adding In-Memory Reviews to File: `add reviews database`
Manually saves (merges) any new reviews from your current session into the data/reviews.txt file without clearing your memory.

Format:
```commandline
add reviews database
```
Expected Output
```commandline
___________________________________________________________________________
Synchronizing in-memory reviews with database...
Added 1 new review(s) to the database.
___________________________________________________________________________
```

### Resetting All Reviews: `reset all reviews`
This clears all reviews from memory only. It does not delete the data/reviews.txt file

Format:
```commandline
reset all reviews
```
Expected Output
```commandline
___________________________________________________________________________
 All reviews have been cleared from memory.
___________________________________________________________________________
```

### Exiting the program: `bye`
Users can exit the program.  

Format:
```commandline
bye
```
Note: If you have added or edited reviews in your session, 
Uniflow will detect these unsaved changes and ask you if you want to save them to the file before exiting.
```commandline
___________________________________________________________________________
You have unsaved reviews in memory. Do you want to save them before exiting? (yes/no)
```

# Command Summary (Uniflow)

## Module Management
| **Action**          | **Format and Examples**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | **Explanation**                                                                       |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| **insert**          | `insert i/MODULE_CODE n/NAME d/DAY f/START_TIME t/END_TIME s/SESSION_TYPE` <br> *Example:* `insert i/CS2113 n/Software Engineering d/Monday f/14:00 t/16:00 s/Lecture`                                                                                                                                                                                                                                                                                                                                   | Adds a new module to your timetable                                                   |
| **delete**          | `delete index/MODULE_INDEX` <br> *Example:* `delete index/1`                                                                                                                                                                                                                                                                                                                                                                                                                                             | Deletes a module by its list index                                                    |
| **list**            | `list`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   | Lists all modules currently added                                                     |
| **show timetable**  | `show timetable`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | Displays all modules with full details                                                |
| **reset timetable** | `reset timetable`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        | Removes all modules from your timetable (cannot be undone)                            |
| **filter**          | `filter day/DAY` <br> e.g., `filter day/Monday` - Shows all Monday modules <br> `filter type/SESSION_TYPE` <br> e.g., `filter type/tutorial` - Shows all tutorial sessions <br> `filter id/MODULE_CODE` <br> e.g.,`filter id/CS` - Shows all modules with "CS" in ID <br> `filter name/MODULE_NAME` <br> e.g., `filter name/Engineering` - Shows modules with "Engineering" in name <br> `filter hastutorial` - Shows modules with tutorials <br> `filter notutorial` - Shows modules without tutorials | Filters modules by given criteria such as day, session type, or presence of tutorials |

---

## GPA Calculator
| **Action**          | **Format and Examples**                                                                                                      | **Explanation**                                               |
|---------------------|------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------|
| **addgrade**        | `addgrade c/COURSE_CODE cr/NUMBER_OF_CREDITS g/GRADE m/IS_MAJOR` <br> *Example:* `addgrade c/CS2113 cr/4 g/A m/true`         | Adds a grade entry for a course to your permanent record      |
| **addtestgrade**    | `addtestgrade c/COURSE_CODE cr/NUMBER_OF_CREDITS g/GRADE m/IS_MAJOR` <br> *Example:* `addtestgrade c/CS2113 cr/4 g/A m/true` | Adds a *temporary* grade for GPA projection                   |
| **removegrade**     | `removegrade INDEX` <br> *Example:* `removegrade 3`                                                                          | Removes a grade entry for a course from your permanent record |
| **removetestgrade** | `removetestgrade INDEX` <br> *Example:* `removetestgrade 3`                                                                  | Removes a course from the *temporary* grade record            |
| **showgrade**       | `showgrade`                                                                                                                  | Displays what courses are saved in the permanent record       |
| **showtestgrade**   | `showtestgrade`                                                                                                              | Displays what courses are currently in the temporary record   |
| **gpa**             | `gpa`                                                                                                                        | Computes cumulative GPA and major GPA based on stored grades  |
| **projectgpa**      | `projectgpa`                                                                                                                 | Computes predicted GPA including test grades                  |

---

## Review Management
| **Action**               | **Format and Examples**                                                                                          | **Explanation**                                                    |
|:-------------------------|:-----------------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------|
| **addreview**            | `addreview c/MODULE_CODE u/USERNAME r/REVIEW_TEXT` <br> *Example:* `addreview c/CS2113 u/John r/Great!`          | Adds a review **to memory** (session-only)                         |
| **review**               | `review MODULE_CODE` <br> *Example:* `review CS2113`                                                             | Displays all reviews **from memory**                               |
| **editreview**           | `editreview c/MODULE_CODE u/USERNAME r/NEW_REVIEW_TEXT` <br> *Example:* `editreview c/CS2113 u/John r/Improved!` | Edits an existing review **in memory**                             |
| **deletereview**         | `deletereview c/MODULE_CODE u/USERNAME` <br> *Example:* `deletereview c/CS2113 u/John`                           | Deletes your review **from memory**                                |
| **findreview**           | `findreview [c/MODULE_CODE] [u/USERNAME]` <br> *Example:* `findreview u/John`                                    | Searches for reviews **in memory** by module or user               |
| **amount reviews**       | `amount reviews [c/MODULE_CODE] [u/USERNAME]` <br> *Example:* `amount reviews c/CS2113`                          | Counts reviews **in memory** by module or user                     |
| **load reviews**         | `load reviews`                                                                                                   | Clears memory and loads reviews from file (prompts to merge)       |
| **add reviews database** | `add reviews database`                                                                                           | Manually merges reviews from memory into the file (saves progress) |
| **reset all reviews**    | `reset all reviews`                                                                                              | Clears all reviews **from memory only** (does not touch the file)  |                                                                                                    | Deletes all reviews and resets the review storage |

---

## Course Ratings
| **Action**      | **Format and Examples**                                   | **Explanation**                            |
|-----------------|-----------------------------------------------------------|--------------------------------------------|
| **rate (add)**  | `rate MODULE_CODE RATING` <br> *Example:* `rate CS2113 4` | Adds a numerical rating (1–5) for a module |
| **rate (view)** | `rate MODULE_CODE` <br> *Example:* `rate CS2113`          | Displays the average rating for a module   |

---

## Score Breakdown
| **Action**       | **Format and Examples**                                                                                                        | **Explanation**                                 |
|------------------|--------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------|
| **score (add)**  | `score MODULE_CODE component1:value1 component2:value2 ...` <br> *Example:* `score CS2113 exam:50 project:30 participation:20` | Adds a new score breakdown for a course         |
| **score (view)** | `score MODULE_CODE` <br> *Example:* `score CS2113`                                                                             | Displays the saved score breakdown for a module |

---

## System and Utility Commands
| **Action** | **Format and Examples** | **Explanation**                                     |
|------------|-------------------------|-----------------------------------------------------|
| **bye**    | `bye`                   | Exits the program (prompts to save unsaved reviews) |

---

## Data Persistence
| **File Location**  | **Stored Data**     |
|--------------------|---------------------|
| `data/modules.txt` | Timetable modules   |
| `data/ratings.txt` | Module ratings      |
| `data/reviews.txt` | Module reviews      |
| `data/grades.txt`  | Saved course grades |

**Note on Data Persistence:**
Most data (Timetable, Grades, Scores, Ratings) is saved automatically.
**Reviews are different.** They operate in a "RAM-first" mode. Your reviews are **NOT** saved automatically.
To save reviews, you must either:
1.  Say **"yes"** when prompted by the `bye` or `load reviews` commands.
2.  Manually run the `add reviews database` command.

---
