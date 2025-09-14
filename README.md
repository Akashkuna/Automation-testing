☕ CoffeeCart Automation Framework
This project automates testing of the CoffeeCart web application using Java, Selenium WebDriver, TestNG, ExtentReports, and Log4j2.
________________________________________
📁 Project Structure
CoffeeCart-Automation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/coffeecart/
│   │   │       ├── pages/              # Page Object classes
│   │   │       ├── utils/              # Utility classes
│   │   │       ├── data/               # DataProviders
│   │   │       ├── managers/           # WebDriver, Logger, Config Manager
│   │   │       └── listeners/          # TestNG Listeners (ExtentReports etc.)
│   ├── test/
│   │   └── java/com/coffeecart/tests/  # Test classes (Menu, Cart, Checkout)
├── test-output/                        # TestNG default reports
├── screenshots/                        # Captured screenshots (on failure)
├── logs/                               # Log4j2 log files
├── testng.xml                          # TestNG Suite configuration
└── pom.xml                             # Maven dependencies and build
________________________________________
✅ Features
•	Right-click support to trigger add-to-cart dialog
•	Upsell banner validation when 3+ items selected
•	Cart persistence after page refresh
•	Checkout form validations
•	Screenshot capture on failure
•	HTML test reports via ExtentReports
•	Central logging with Log4j2
•	Data-driven testing via TestNG DataProvider
________________________________________
🧪 How to Run
1.	Open project in IntelliJ
2.	Run mvn clean test or right-click testng.xml and select Run
3.	View results:
–	test-output/ folder for TestNG reports
–	extent-reports/ folder for HTML reports (if configured)
–	screenshots/ folder for failure captures
________________________________________
⚙ Tech Stack
•	Java 17+
•	Selenium 4.23.0
•	TestNG 7.10.2
•	Maven
•	Log4j2
•	ExtentReports 5.1.1
________________________________________
🙋 Author
Akash Kuna R

