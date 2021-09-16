# Xendit QA Assessment

---
> To view the actual test execution of the test scripts, please view (or download) the recording here: 
> [Xendit QA Assessment](https://drive.google.com/file/d/1EozJNjUsEXOH6R1nQm2Ms7AFtRMyfanw/view?usp=sharing)

## WHAT'S IN THIS REPOSITORY?
1. Prerequisites
2. Test Cases
3. Test Scripts

### Prerequisites
This repository is a Maven project to install and utilize the required dependencies. Please see the ff. prerequisites:
- [x] `IntelliJ IDEA` → The use of IntelliJ IDEA is highly preferred. However, other IDEs such as Eclipse can do as well.
- [ ] `Maven` → The repository was created as a `Maven` project; thus, it needs the plugin. Some IDEs including `IntelliJ` come with it as
  a default plugin. However, for instance that it does not, it needs to be installed. [[Download Maven](https://maven.apache.org/download.cgi)]
- [x] `Java 8` → The `maven.compiler.plugin` is set to 1.8. [[Download Java 8 - Amazon Corretto (Open Source)](https://github.com/corretto/corretto-8/releases)]

### Test Cases
There are three (3) test cases in this repository to cover Addition, Subtraction, and Division arithmetic operations. The test
cases are located in [Test Cases](https://github.com/zainodden02/xendit-qa-exam/tree/main/testcases) directory. Each test case
consists of an Objective, Verification and Failure point, Steps to reproduce, Expected results, and test data.

### Test Scripts
There are three (3) main test scripts. They are located in [src/main/java/scripts](https://github.com/zainodden02/xendit-qa-exam/tree/main/src/main/java/scripts)
directory. Each test script has its own separate test data file to dynamically specify the desired data for test execution. The
class name of the test script corresponds to the summary of the test case.

#### How does the script work?
The scripts use the Robot framework to send a Keyboard event on the browser to interact with the calculator canvas. After performing
the actions, the script will take a screenshot of the canvas and will retrieve the computed value as displayed on the display
panel of the calculator. The retrieved value from the calculator is compared against the total value of the given data. This approach 
was made possible through the use of Tesseract OCR library.

The screenshots are stored in `target/screenshots` directory. The file with `_TotalResults` suffix is used to 
capture the value on the display panel.

#### How to execute the scripts?
A test plan named `TestPlan.xml` is located in the test plan package. This can be used to run all the test scripts in the same
test suite. A single script can also be executed. Update the test data located in `data` package if needed.

> **NOTE**: The functions and scripts are not fully furnished to handle certain errors, but some error handling are already
> available.