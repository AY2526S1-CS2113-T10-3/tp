# Maksymilian Paczyński - Project Portfolio

### Timetable Management

#### ShowTimetableCommand
Implemented a new command to display the full timetable in a clean, structured format.

* Replaced the old `list` command with a clearer and more readable presentation.
* Shows module ID, name, day, time, and session type.
* Integrated with the `UI` class for consistent output across commands.

**Example usage:** `show timetable`

**Example output:** `Module[ID=CS2113, Name=SoftwareEng, Type=Lecture, Day=Friday, Time=16:00–18:00]`

#### ResetTimetableCommand
Created a command to clear the timetable completely.

* Allows users to reset all sessions at once.
* Displays confirmation messages through the UI.
* Ensures timetable consistency after removal of all sessions.

**Example usage:** `reset timetable`

**Example output:** `Your timetable has been reset successfully.`

---

### Review System

#### Core Review Management (Add, Edit, Delete, View)
Implemented the core review features, enabling users to view (`review`), add (`addreview`), edit (`editreview`), and delete (`deletereview`) reviews for courses.

* Developed and extended supporting classes (`ReviewManager`, `ReviewStorage`) for data persistence.
* Updated `Parser` to recognize and handle all core review-related commands.
* Maintained consistent UI messages and proper error handling for invalid inputs.

**Example usage:** `addreview c/CS2113 u/John r/Very useful module`  
`review CS2113`  
`editreview c/CS2113 u/John r/Updated review`  
`deletereview c/CS2113 u/John`

**Example output:** `Review added successfully.`

#### FindReviewCommand
* Implemented the `FindReviewCommand` for flexible searching of course reviews.
* The command supports filtering by:
    * User only (`findreview u/USER`)
    * Course only (`findreview c/COURSE`)
    * A combination of user and course (`findreview u/USER c/COURSE`)
* Integrated directly with the `ReviewManager` to ensure access to the most current, synchronized data.

**Example usage:** `findreview u/Mary`  
`findreview c/CS2113`  
`findreview u/John c/CS2113`

**Example output:** `Reviews by John in CS2113:`  
`- CS2113 - John: Very useful module`

#### ReviewAutosave / ReviewSyncManager (Data Persistence)
* Implemented a robust data persistence mechanism to ensure data integrity upon program shutdown.
* Utilized a **shutdown hook** that is triggered just before the application closes.
* This hook automatically calls `reviewManager.flush()`, guaranteeing that all in-memory review data is written to the storage file.
* This feature prevents data loss, even in cases of unexpected or abrupt program termination.

#### ReviewCleaner
* Introduced `ReviewCleaner` to work alongside the `ReviewSyncManager`.
* This component automatically removes incomplete or corrupted review entries from the storage file during the save process.
* This ensures high data integrity and prevents invalid entries from being loaded in future sessions.

#### ReloadReviewsCommand
* Developed the `ReloadReviewsCommand` and integrated it into the `Parser`.
* This command allows a user to manually refresh the in-memory review data by reloading it from the storage file without restarting the application.
* Primarily useful for development and testing purposes, especially when the data file is modified externally.

**Example usage:** `reload reviews`

**Example output:** `Reviews reloaded successfully from file.`

#### ResetReviewsCommand
Implemented a command to restore reviews to their default state.

* Clears all current review data from memory and reloads the default review set from a clean source.
* Useful for testing or resetting the application to its initial configuration.
* Helps maintain consistent data across testing sessions.
