# CampusPay
Job Board app for students

## Project Setup

```
CampusPay/
├── CampusPayApp/
│   └── src/
│       └── main/
│           └── java/
│               └── com/example/campuspayapp/
│                   ├── ui/         // Classes related to the user interface (screens)
│                   ├── api/        // Classes for handling communication with the backend
│                   ├── utils/      // Utility classes (e.g., for validation)
│                   └── HelloApplication.java       // The main entry point of the application
└── README.md
```

## Sitemap

```
Splash Screen
│
├── Login Page
│   ├── Student Login (Tab)
│   └── Employer Login (Tab)
│
├── Employer Registration Page
│
├── Home Page
│   ├── Job Listing (Container)
│   │   └── Job Summary (List Item)
│   │       └── View Job Details (Button - Navigates to Job Details Page)
│   └── Navbar
│       ├── Search (Navigates to Search Page)
│       ├── Logout (Navigates to Logout Page)
│       └── Profile (Navigates to Profile Page)
│
├── Job Details Page
│   ├── Job Summary (Text)
│   ├── Apply to Job (Checkbox - For Students)
│   └── Applicants List (For Employers)
│       └── Applicant Profile (List Item)
│           ├── Accept Application (Button)
│           └── Reject Application (Button)
│
├── Profile Setups Page
│   ├── Student Profile Setup
│   └── Employer Profile Setup
│
├── Profile Page
│   ├── Student Profile
│   │   ├── Student Information (Preview)
│   │   ├── Additional Information (Editable)
│   │   └── List of Applied Jobs
│   └── Employer Profile
│       ├── Registration Details (Editable)
│       └── List of Created Jobs
│
├── Search Page
│   ├── Search Input Field
│   └── Search Results (Container)
│       └── Job Summary (List Item)
│           └── View Job Details (Button - Navigates to Job Details Page)
│
└── Logout Page
    ├── Confirmation Text
    ├── Cancel (Button - Navigates back to previous page/Home Page)
    └── Logout (Button - Navigates to Login Page)
```
