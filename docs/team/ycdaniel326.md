# Chiu Yin Chi - Project Portfolio Page  
  
## Overview
**Uniflow** is a command-line university students management assistant that helps students organize their timetables, track academic progress, and collaborate through course reviews and ratings.
It allows users to **add modules, detect timetable clashes, record grades, calculate GPA, add reviews,** and **rate courses** using intuitive text commands.

This product is designed for university students who prefer a fast, keyboard-based workflow and need a compact way to manage academic and timetable data without relying on GUI-based tools.

## Summary of Contributions

### Grade Manager and GPA calculator 

  #### Grade Manager and Storage
- Allows users to store their completed course's grade information in a permanent course record.   
    
  Related command: `addgrade`  
  Related codes in the project: `Course`, `CourseRecord`, `AddGradeCommand`  
- Added a separate list that stores the record of major required courses specifically.  
  *Note: Users can indicate whether a course is a major-required course when they add the course.*  
  *By indicating so, users can compute their cumulative major-required courses GPA (or Major GPA) later.*      
    
  Related codes: `Course`, `CourseRecord`  
- The record is saved in a text file and can be reused in the future.  
    - Every time the users add a completed grade, it will be saved in the permanent record, and will not lose the record even if they exit Uniflow.
    - Every time the users run Uniflow, the course records will be loaded from the saved file.
    
  Related codes in the project: `AddGradeCommand`, `GradeStorage`  
- Allows users to manage and organise their grade records by displaying and amending the records.  
    - With `showgrade`, users can see their detailed course record listed out. 
    - Users can operate on both the permanent grade record, and the temporary record that stores temporary predicted grades.  
    - For example, `removegrade` allows user to remove a grade record from the permanent record, while `removetestgrade` allows users to remove a predicted course grade from the temporary record.
    - The saved file will be updated accordingly. 

  Related commands: `showgrade`, `showtestgrade`, `removegrade` and `removetestgrade`  
  Related codes in the project: `ShowGradeCommand`, `ShowTestGradeCommand`, `RemoveGradeCommand`, `RemoveTestGradeCommand`  

 #### Compute GPA feature
- Allows users to compute their cumulative GPA and major-specific GPA respectively, based on their completed course record.  
- Allows them to keep track of their academic performance.  
      
  Related command: `gpa`  
  Related codes in the project: `CourseRecord`, `ComputeGpaCommand`
  
 #### Project GPA feature
- Allows users to compute a projected GPA based on their predictions on their upcoming courses.
    - Users can enter their predicted grades for not yet completed courses.  
    - They can then use it to compute a projected GPA based on the predicted grades and grades attained in completed courses.  
    - The added courses will only be stored **temporarily**, but will not affect their current real record of grades.  
    - The permanent course record and temporary record will be used together for the computation.  
    - This function allows users to simulate possible GPA results and have a better idea of their academic targets.
    - They can then set potential strategies for courses they are going to complete.  
    
  Related command: `addtestgrade`, `projectgpa`  
  Related codes in the project: `CourseRecord`, `AddTestGradeCommand`, `ProjectGpaCommand`
#### Other Parts 
- `Parser`, `Uniflow`: amended codes in `Parser` and `Uniflow` for executing the commands for GPA calculator.
   - Included codes for checking and validating users' input.  
   - Gives a friendly reminder and guidelines if the users have entered invalid input/format.  
- Added JUnit tests for some of the functions.

## Code contributed
- [RepoSense Link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=ycdaniel326&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=ycdaniel326&tabRepo=AY2526S1-CS2113-T10-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
  - Shows my commits, codes contribution, and changes in files.

## User Guide and Developer Guide
- Contributed to the parts related to the features I implemented, for example, those related to CourseRecord and GPA calculator.
- Included detailed description on how to use the commands, with examples, expected output and explanation included.

## Team Contribution
- Brainstormed and set the intial direction and idea of the product, such as setting the direction to be a student-oriented app and suggested features to be included.  
- Reviewed, approved and merged the pull requests of other groummates.

