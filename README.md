# Health Monitoring System

The Health Monitoring System is a comprehensive application designed to empower users to track and manage their health data. It enables users to monitor various health metrics, set medication reminders, and receive personalized health recommendations. Additionally, it provides healthcare professionals with access to their patients' health data, facilitating better patient care.

## Features

- **Health Data Tracking**: Log and track various health metrics such as weight, height, steps, heart rate, and sleep duration.
- **Medication Reminders**: Set reminders for medications to never miss a dose again.
- **Personalized Health Recommendations**: Receive health recommendations based on your tracked health data.
- **Doctor Portal**: Allows healthcare professionals to access and manage their patients' health data.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- PostgreSQL Database
- Git

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourorganization/health-monitoring-system.git
   cd health-monitoring-system
   ```
2. **Database Setup**
    - Create a PostgreSQL database named health_monitoring.
    - Execute the SQL scripts located in script/create to create the necessary tables.
    - Execute the SQL scripts located in script/insert to insert some data or create your own.

3. **Run the Application**
    - Compile the source code using the Java compiler:
    ``` bash 
    javac HealthMonitoringApp.java 
    ```

    - Run the compiled Java program:
    ``` bash 
    java HealthMonitoringApp.java 
    ```
## Usage
After starting the application, navigate to the terminal or command prompt window. The application will guide you through its functionalities with interactive prompts.

## Contributing
Contributions to the Health Monitoring System are welcome! Please refer to our Contributing Guidelines for more information.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
