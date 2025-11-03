# Seah Song Li — Project Portfolio

## Overview
**Uniflow** is a command-line university module management assistant that helps students organize their timetables, track academic progress, and collaborate through course reviews and ratings.
It allows users to **add modules, detect timetable clashes, record grades, calculate GPA, add reviews,** and **rate courses** using intuitive text commands.

This product is designed for university students who prefer a fast, keyboard-based workflow and need a compact way to manage academic and timetable data without relying on GUI-based tools.

---

## Summary of Contributions

### Timetable Management
#### `InsertCommand`
**Purpose:**  
Adds a new module to the user’s timetable while checking for time clashes.

**Key Features:**
- Constructs a `Module` object from parsed user input:
    - `i/` → Module ID
    - `n/` → Module Name
    - `d/` → Day
    - `f/` → Start Time
    - `t/` → End Time
    - `s/` → Session Type (optional)
- Detects schedule conflicts using `ModuleList.findClash(module)`
- Prompts user confirmation (`yes` / `no`) when a clash is found
- Adds the module via `ModuleList.addModule(module)` after confirmation
- Displays confirmation and updated module count through `UI.showMessage()`
- Does not terminate the app (`isExit() = false`)

**Example Usage:**
```bash
insert i/CS2113 n/SoftwareEng d/Mon f/10:00 t/12:00 s/Lecture
```

**Example Output:**
```bash
Got it! I've added this module:
CS2113 SoftwareEng (Mon 10:00–12:00 Lecture)
Now you have X module(s) in the list.
```

#### `DeleteCommand`
**Purpose:**  
Removes an existing module from the list based on its module ID.

**Key Features:**
- Deletes the module using `ModuleList.deleteModuleById(moduleId)`
- Displays deleted module details and updated module count
- Uses consistent message formatting via `UI.showMessage()`
- Does not terminate the app (`isExit() = false`)

**Example Usage:**
```bash
delete i/CS2113
```

**Example Output:**
```bash
Noted. I've removed this module:
CS2113 SoftwareEng
Now you have X module(s) left in the list.
```

### Command Parsing & Routing
#### `Parser`
**Purpose:**  
Acts as the central command router that validates user input and instantiates the appropriate command class.

#### Parsing Logic Overview
##### `parseInsertCommand`
**Expected Format:**
```bash
insert i/<id> n/<name> d/<day> f/<start> t/<end> [s/<type>]
```

##### `parseDeleteCommand`
**Expected Format:**
```bash
delete index/<module_index>
```

### User Interface Layer (`UI`)
**Purpose:**  
Handles all user interaction, input, and output formatting.

**Key Features:**
- Displays branded welcome banner via `showWelcome()`
- Reads user input with `readCommand()`
- Consistent dividers and formatting in `showMessage()` and `showError()`
