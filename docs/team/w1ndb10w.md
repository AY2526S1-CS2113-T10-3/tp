# Cheng Lefan - Project Portfolio Page

## Overview
**Uniflow** is a command-line university module management assistant that helps students organize their timetables, track academic progress, and collaborate through course reviews and ratings.
It allows users to **add modules, detect timetable clashes, record grades, calculate GPA, add reviews,** and **rate courses** using intuitive text commands.

This product is designed for university students who prefer a fast, keyboard-based workflow and need a compact way to manage academic and timetable data without relying on GUI-based tools.

---

## Summary of Contributions
### Code Contributed
[RepoSense link](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=w1ndb10w&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

---

### Enhancements Implemented
#### 1. Course Search and Filtering Feature
- **What it does**: Allows users to search and filter courses with command `filter` based on various criteria such as course code, name, or other attributes.
- **Justification**: This enhancement is essential for users managing multiple courses across semesters. It significantly improves user experience by enabling quick access to specific course information without having to scroll through the entire course list.
- **Highlights**: The implementation required careful consideration of search algorithms and filtering logic to ensure efficient and accurate results. I designed the feature to be extensible, allowing for future enhancements such as advanced filtering options.

#### 2. Review Management System
- **Review Post Feature**: `addreview`
    - Implemented the functionality for users to create and post reviews for their courses, allowing students to share their experiences and insights.
- **Review Edit Feature**: `editreview`
    - Developed the editing capability that enables users to modify their previously posted reviews, ensuring review information remains current and accurate.
- **Review Delete Feature**: `deletereview`
    - Developed the deletion functionality to allow users to remove reviews they no longer wish to keep.
- **Justification**: The review system adds significant value to the application by creating a knowledge-sharing platform where students can help each other make informed course selections. This peer-to-peer information exchange is crucial for academic planning.
- **Highlights**: The implementation required careful design of the data structure to efficiently store and retrieve reviews. I ensured data integrity by implementing proper validation and error handling mechanisms.

#### 3. Testing and Code Quality Enhancements
- **Comprehensive JUnit Test Suite**: Developed and implemented extensive test coverage across multiple test files:
    - **New Test Files Created**:
        - **FilterCommandTest**: 20+ test cases for basic/advanced filtering, case-insensitive matching, OR logic, edge cases, and special characters
        - **ScoreManagerTest**: Score calculation, GPA computation, credit weighting, and grade format variations
        - **GradeStorageTest**: File I/O with detailed logging, data persistence, multiple courses, and edge cases (non-existent/empty files)
        - **ModuleStorageTest**: Module persistence, data integrity verification, and error handling for corrupted/missing files
        - **CommandTest**: Command parsing, validation, and execution across various command types
        - **UITest**: Output validation, formatting verification, and display consistency
    - **Enhanced Existing Test Files**:
        - **ParserTest**: Improved parameter extraction, boundary conditions, complex parsing, and error handling
        - **RatingManagerTest**: Expanded rating operations, validation logic, and edge cases
        - **ReviewManagerTest**: Enhanced review operations, indexing edge cases, and persistence verification
        - **ScoreCommandTest**: Improved command execution, output formatting, and ScoreManager integration
        - **UniflowTest**: Enhanced integration testing for application flow and component interactions

- **Enhanced Debugging & Assertions**: Implemented comprehensive logging and assertions across all test suites to trace execution, validate state transitions, and catch bugs early. Strategic assertion placement ensures data integrity at critical checkpoints.

- **Test-Driven Development**: Utilized test cases to identify and fix bugs in parameter extraction, file persistence, review operations, and score calculations across diverse scenarios.

- **Justification**: Comprehensive testing significantly improves code coverage, prevents regressions, and ensures features remain functional as code evolves. Detailed logging and assertions enable rapid debugging and maintain high quality standards.

- **Highlights**:
    - Established robust testing infrastructure with multiple new test files from scratch
    - Test suites cover extensive edge cases: special characters, varying data formats, null/empty inputs, file operations
    - Detailed logging with expected vs. actual values for efficient troubleshooting
    - Comprehensive assertions validate object states, data persistence, and error conditions

---

### Contributions to the User Guide
- **Documentation/user guide updates**: Contributed comprehensive documentation for the `filter` feature with various test cases.
- Wrote clear, user-friendly instructions with examples to help users understand how to effectively use these features.
- Included common use cases and troubleshooting tips to enhance user experience.
- Added table of contents and organized sections for easy navigation.
- Table formatting and visual aids to improve readability.

---

### Contributions to the Developer Guide
- **Documentation/diagrams**:
  - Set up the initial structure and framework for technical diagrams in the Developer Guide.
  - Summarize and make`Command Execution Flow` diagram.
  - Summarize and make `Architecture Overview` diagram.
- **Documentation/developer guide**:
  - Established the overall structure and sections of the Developer Guide to ensure consistency and completeness.
  - Write the `filter` feature.
- **Documentation/embed diagrams in developer guide**: Integrated UML diagrams and other technical diagrams into the Developer Guide to illustrate system architecture and component interactions.
- Created detailed technical documentation explaining the implementation of the search/filter feature and the review management system.
- Designed and updated UML diagrams to visualize the architecture and workflow.

---

### Contributions to Team-Based Tasks
- Managed releases `v1.0`, `v2.0`, and `v2.1` on GitHub, ensuring smooth deployment and version control.
- Set up and kept maintaining the project repository organization and structure, e.g., `issue trackers`, `milestones`, `labels`.
- Established documentation framework for both `User Guide` and `Developer Guide`, ensuring consistency across team contributions.
- Contributed to project planning and timeline management, helping to set realistic milestones and deadlines.
- Assisted in organizing the documents and ensuring all information was properly documented in the `Project Portfolio Pages`.
- Actively participated in bug tracking and issue resolution discussions.
- Actively participated in team meetings and discussions regarding project direction, feature prioritization, and implementation strategies.

---

### Review/Mentoring Contributions
- Reviewed and provided feedback on pull requests from team members.
- Collaborated with teammates to resolve merge conflicts and integration issues.
- Shared knowledge about documentation best practices with the team.

---

Note: This PPP demonstrates my contributions across multiple aspects of the project, including feature development, documentation, testing, and team collaboration. The enhancements I implemented are non-trivial, requiring careful design consideration, robust implementation, and comprehensive testing to ensure they integrate seamlessly with the existing system.
