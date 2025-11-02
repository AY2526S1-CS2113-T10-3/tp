# Maksymilian Paczyński - Project Portfolio

## Overview
Uniflow is a command-line assistant that helps students manage university modules, timetables, grades, and course reviews efficiently using text-based commands.

## Summary of Contributions

### Timetable Management

#### ShowTimetableCommand
Implemented a new command to display the full timetable in a clean, structured format.

* Replaced the old `list` command with a clearer presentation.
* Shows module ID, name, day, time, and session type.
* Integrated with the `UI` class for consistent output.

**Example usage:** `show timetable`  
**Example output:** `Module[ID=CS2113, Name=SoftwareEng, Type=Lecture, Day=Friday, Time=16:00–18:00]`

#### ResetTimetableCommand
Created a command to clear the timetable completely.

* Allows users to reset all sessions at once.
* Displays confirmation messages through the UI.

**Example usage:** `reset timetable`  
**Example output:** `Your timetable has been reset successfully.`

### Review System

#### ReviewCommand, AddReviewCommand, EditReviewCommand, DeleteReviewCommand, FindReview
Implemented and expanded the review system, enabling users to view, add, edit, delete, and search reviews for courses.

* Added `FindReview` command to search for reviews by a specific user across all or selected courses.
* Extended `ReviewManager` with new methods to support lookup by user and improve data access.
* Updated `Parser` to recognize and handle all review-related commands.
* Maintained consistent UI messages and error handling.

**Example usage:** `addreview c/CS2113 u/John r/Very useful module`  
`findreview u/John`  
`findreview u/John c/CS2113`

**Example output:** `Reviews by John in CS2113:`  
`- CS2113 - John: Very useful module`