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



## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
