# Patryk Mrozek - Project Portfolio Page

## Overview
**Uniflow** is a command-line university module management assistant that helps students organize their timetables, track academic progress, and collaborate through course reviews and ratings.
It allows users to **add modules, detect timetable clashes, record grades, calculate GPA, add reviews,** and **rate courses** using intuitive text commands.

This product is designed for university students who prefer a fast, keyboard-based workflow and need a compact way to manage academic and timetable data without relying on GUI-based tools.

### Summary of Contributions
#### Course Rating System
- `RateCommand` - Executes rating-related commands:
  - Adds a new rating using `rate <MODULE> [RATING]`
  - Displays the average rating with `rate <MODULE>`
  - Validates that only existing courses can be rated through `CourseRecord`
- `RatingManager` — Handles in-memory rating logic using a `HashMap<String, RatingStats>`
- `RatingStats` — Tracks sum and count of ratings per course and computes the average
- `RatingStorage` - Manages saving and loading ratings to/from `data/ratings.txt`
- Updated `Parser` — Added parsing logic for the new `rate` command, distinguishing between add and view modes

#### Score Breakdown Feature
- `ScoreCommand` - Allows user to record assessment breakdowns (e.g. exam, project, attendance..) for each module
  - Adds a new score breakdown using `score <MODULE> string:score string2:score2...`
  - Example: `score CS2113 exam:50 project:30 participation:20`
- Parses user input to interpret key-value pairs in the format `component:score`