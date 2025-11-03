# Patryk Mrozek - Project Portfolio Page

## Overview
**Uniflow** is a command-line university module management assistant that helps students organize their timetables, track academic progress, and collaborate through course reviews and ratings.
It allows users to **add modules, detect timetable clashes, record grades, calculate GPA, add reviews,** and **rate courses** using intuitive text commands.

This product is designed for university students who prefer a fast, keyboard-based workflow and need a compact way to manage academic and timetable data without relying on GUI-based tools.

---

### Summary of Contributions
[RepoSense](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=patrykmrozek&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00:00:00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=patrykmrozek&tabRepo=AY2526S1-CS2113-T10-3/tp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

---

### 1. Major Feature — Course Rating System
- Implemented the **`RateCommand`**, enabling users to add and view ratings:
    - `rate CS2113 5` → adds a rating.
    - `rate CS2113` → displays the current average rating.
- Developed **`RatingManager`**, handling rating logic via `HashMap<String, RatingStats>` for quick access and updates.
- Created **`RatingStats`** to store cumulative ratings and counts for each module, computing averages dynamically.
- Implemented **`RatingStorage`** for persistent storage across sessions (`data/ratings.txt`).
- Extended **`Parser`** to distinguish between add/view modes based on user input.
- This feature allows students to collectively evaluate courses, helping peers make informed module choices.
- Added **Rating Management UML diagrams** (Class + Sequence) to Developer Guide for v2.1.


---

### 2. Major Feature — Score Breakdown System (Original Design + Rework)
- Designed and implemented the **`ScoreCommand`** from scratch to allow users to record assessment breakdowns for each module.
    - Example: `score CS2113 exam:50 project:30 participation:20`
    - Query mode: `score CS2113` displays the saved breakdown.
- Built the **core parsing logic** to handle flexible `component:score` input, including spacing, ordering, and validation.
- Later **reworked the system** into a modular architecture with persistent storage:
    - Added **`ScoreManager`** for managing all breakdown logic independently from the command layer.
    - Implemented **`ScoreStorage`** to persist breakdowns to `data/scores.txt`.
    - Updated **`Uniflow`** to initialize a shared `ScoreManager` for consistent access across sessions.
- The rework improved code maintainability, modularity, and data persistence while preserving the original functionality.
- **Overall Impact**: The score system is one of Uniflow’s core academic tracking features, giving users a clear overview of module weightings and progress distribution.
- Added **Score Management UML diagrams** (Class + Sequence) to Developer Guide.
---

### 3. Module Management Enhancements
- Implemented **`ModuleStorage`**, a new class that allows persistent saving and loading of all user modules between sessions.
    - Integrated clean file I/O operations for module data stored in `data/modules.txt`.
    - Improved system resilience through robust exception handling during save/load operations.
- Enhanced **`ModuleList`** with key utility methods:
    - `normalize()` ensures consistent comparison by converting module IDs to uppercase and trimming whitespace.
    - `getModuleByID()` provides reliable lookup of modules regardless of user input formatting.
    - `doesExist()` allows quick checks for module presence to prevent duplicate entries.
- Improved **`CourseRecord`** with new helper methods for better course lookup and validation:
    - `hasCourse()` — validates existence of a course ID before operations.
    - `getCourse()` — retrieves the course object safely with case-insensitive comparison.
- **Impact**: These changes improved the accuracy, reliability, and user experience of Uniflow’s core module management operations, ensuring consistent behavior across commands.

---

### 4. Testing and Code Quality Enhancements
- **JUnit Test Cases**: Wrote extensive unit tests for `RateCommand`, `ScoreCommand`, and their managers/storage components.
    - Covered valid, invalid, and edge cases (e.g., malformed input, negative values, non-existent modules).
- **Assertions Implementation**: Added internal assertions to validate data integrity during parsing and saving.
- **Checkstyle & CI Compliance**: Fixed and standardized method naming, class documentation, and structure across all new files.
- **Justification**: These enhancements significantly improved reliability, test coverage, and maintainability.
- **Highlights**:
    - Tests simulate realistic user interactions and error recovery.
    - Command parsing and persistence behavior validated through integration tests.

---

### 5. Contributions to the User Guide
- Authored clear, example-driven documentation for:
    - **Course Rating System** — Explained command usage, examples, and error handling.
    - **Score Breakdown System** — Documented both input format and query mode for retrieving breakdowns.
- Added command examples to “Instructions for Manual Testing” section.
- Included edge cases, invalid input examples, and usage hints for clarity.
- Improved overall user experience with structured sections and consistent formatting.
---

### 6. Contributions to the Developer Guide
- Documented implementation details for both **Rating** and **Score** systems.
- Created UML diagrams showing the interaction between:
    - `Parser`, `Command`, `Manager`, and `Storage` layers.
    - Load/save data flow for persistent storage.
- Added explanation of **ModuleStorage integration**, describing how module data is serialized and deserialized.
- Updated diagrams and explanations after reworking the Score system to reflect the new architecture.
- Contributed to **Implementation Details**, **Key Design Patterns**, and **Design Details** sections.
---

### 7. Contributions to Team-Based Tasks
- Regularly participated in design discussions and helped shape how core managers interact with storage components.
- Standardized naming conventions, error messages, and command syntax across all features.
- Assisted teammates with integration issues related to shared resources and file handling.

---

### 8. Review and Mentoring Contributions
- Reviewed and provided feedback on teammates’ pull requests with a focus on maintainability and adherence to coding standards.
- Assisted in resolving merge conflicts and logic inconsistencies in the `Parser` class during feature merges.
- Supported the final integration of all storage-based managers before v2.1 release.
