# Maksymilian Paczyński - Project Portfolio

### Timetable Management

#### ShowTimetableCommand
* Implemented the `show timetable` command to display the full timetable in a structured format.
* The command presents the module ID, name, day, time, and session type.
* Integrated the command with the `UI` class to ensure consistent output formatting.

**Example usage:** `show timetable`
**Example output:** `Module[ID=CS2113, Name=SoftwareEng, Type=Lecture, Day=Friday, Time=16:00–18:00]`

#### ResetTimetableCommand
* Created the `reset timetable` command to completely clear the timetable.
* The command allows the user to delete all entries simultaneously and displays a confirmation message.

**Example usage:** `reset timetable`
**Example output:** `Your timetable has been reset successfully.`

---

### Review System: "RAM-First" Architecture

Designed and implemented a review system using a "RAM-first" architecture. All operations, such as adding, editing, and deleting, are performed on in-memory data first. The user retains full control over the process of synchronizing data with the persistent storage (the file).

#### In-Memory Review Management
* Implemented core CRUD operations for reviews: `addreview`, `editreview`, and `deletereview`.
* All the above commands modify only the in-memory state in `ReviewManager`, which eliminates I/O latency.
* `ReviewManager` was equipped with a fallback mechanism that activates a "RAM-only" mode if the review file cannot be read, ensuring application stability.

#### Data Read and Search Commands
* **`review`**: The basic command to display all reviews for a specific course from memory.
* **`findreview`**: Implemented an advanced command to search for reviews in memory. It allows filtering by user, course, or both criteria simultaneously.
* **`amount reviews`**: Created a new command to count reviews currently in memory, with filtering analogous to `findreview`.

**Example usage:** `findreview c/CS2113 u/John`
**Example output:** `Reviews by John in CS2113: ...`

---

### Review Persistence Management: User Control

Replaced previous automatic mechanisms with an explicit synchronization model, giving the user control over data persistence.

#### ExitSaveManager: Synchronization on Exit
* Implemented a new class, `ExitSaveManager`, which is invoked by the `bye` command.
* This component detects unsaved changes by comparing the in-memory state (`ReviewManager`) with the file state (`ReviewStorage`).
* If differences are detected, `ExitSaveManager` prompts the user for confirmation ("yes" or "no") to save changes before closing the application, preventing data loss.
* Utilized reflection to invoke the private `detectUnsavedReviews` and `mergeAndSaveReviews` methods, which are shared with `LoadReviewsCommand`, thus avoiding code duplication of the merge logic.

#### LoadReviewsCommand: Merge-on-Load
* Expanded the `load reviews` command with logic to check the in-memory state.
* Before loading data from the file, the command detects unsaved reviews in memory and asks the user for permission to merge them into the file.
* This ensures that work from the current session is not overwritten when reloading data.

**Example usage:** `load reviews`
**Example output:** `There are unsaved reviews in memory. Do you want to add them to the database? (yes/no)`

#### AddReviewsDatabaseCommand: Manual Synchronization
* Implemented the `add reviews database` command for manual synchronization.
* The command merges and appends new reviews from memory to the file on demand, without interrupting the user's session or clearing the in-memory state.

**Example usage:** `add reviews database`
**Example output:** `Added 2 new review(s) to the database.`

#### ResetReviewsCommand: In-Memory Reset
* Implemented the `reset all reviews` command in accordance with the "RAM-first" model.
* The command clears only the review data in memory using `ReviewManager.clearAll()`.
* The persistent storage (the `reviews.txt` file) remains untouched, allowing for application testing from a clean session state.

**Example usage:** `reset all reviews`
**Example output:** `All reviews have been cleared from memory.`