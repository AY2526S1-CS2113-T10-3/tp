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
- **Comprehensive JUnit Test Suite**: Developed extensive JUnit test cases to ensure feature reliability and robustness across the application, significantly improving overall code coverage and quality. Test suites include:
    - **FilterCommandTest**: Created a comprehensive test file with 20+ test cases covering:
        - Basic filtering by course code and name
        - Case-insensitive matching and partial matches
        - Combined filters with OR logic
        - Edge cases (empty list, no matches, null parameters)
        - Special characters and whitespace handling
        - Output formatting validation
    - **Review System Tests**: Covered various scenarios including:
        - Single and multiple review operations
        - Edge cases for review indexing
        - Parameter extraction validation
        - File I/O operations and data persistence
    - **Parser Tests**: Validated parameter extraction logic with multiple parameters and boundary conditions
- **Assertions Implementation**: Enhanced code reliability by implementing assertions throughout the codebase to catch potential bugs and validate program state during development and runtime. Strategic placement of assertions helps identify issues early in the development lifecycle.
- **Test-Driven Debugging**: Utilized test cases to identify and fix bugs in parameter extraction and review persistence, ensuring features work correctly across different scenarios.
- **Code Review and Refactoring**: Conducted thorough code reviews to identify potential issues and refactored code segments for better maintainability, readability, and adherence to coding standards.
- **Justification**: These enhancements are critical for maintaining code quality and preventing bugs. Comprehensive testing ensures that features work as intended and continue to function correctly as the codebase evolves. The test-first approach helps catch regressions early.
- **Highlights**: The test cases cover various edge cases and scenarios, ensuring robust error handling and validation. The assertions help catch bugs early in the development process and prevent runtime errors. Test suite is designed to be maintainable and easily extensible for future features.

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
