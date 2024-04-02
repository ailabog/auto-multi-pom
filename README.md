# A2A.DRIVEN
1. About A2A.DRIVEN
2. Quick Start Guide
2.1  Introduction
2.2  Pre-requisites
2.2.1 Java Setup
2.2.2 Maven Setup
2.2.3 Eclipse Setup
2.2.3.1 JRE Settings
2.2.4 TestNG Setup
2.2.5 AspectJ Setup 
2.3. Global Automation Framework Setup
3. Licensing
3.1 Terms and conditions for A2A.DRIVEN
3.2 Licensing of A2A.DRIVEN dependencies and 3rd party software
4. List of Dependencies and Licenses

## 1. About A2A.DRIVEN
A2A.DRIVEN (Advanced Test Automation Framework) is Nagarro’s proven accelerator for Test Automation projects.

It enables project teams to write tool-agnostic and technology-independent, sustainable test cases, for all end to end business scenarios.

* A2A DRIVEN framework includes support programs, code libraries and a scripting language among other software to help develop and glue together the testing components of the automation project.

* A2A DRIVEN Framework provides a mechanism that guides users through a proper order of steps, applications, and data conversions via a common interface to the process being followed.

## 2. Quick Start Guide
### 2.1 Introduction
The purpose of this document is to provide detailed steps required for setting up the GLOBAL AUTOMATION FRAMEWORK development environment.
### 2.2 Pre-requisites
Following are the tools and their versions should be installed before setting up this automation framework:
| Tools | Version/Release |
| ------ | ------ |
| OS | WINDOWS 64-bit |
| JAVA | JDK 1.8 or later. 64-bit|
| Eclipse | Neon, Win-64-bit |
| Apache Maven | 3.3.9 |
| Appium |  |
| testNG | 6.11.0 |
| Selenium Version | 3.4.0|
|Firefox driver version|   |
|Firefox version on machine| 59.0.2 (64-bit) |
|Chrome driver version|  |
| Internet Explorer version |  |
|Selenium Grid |  |
|Android SDK Version |  |

#### 2.2.1 Java Setup
a. Download JDK 1.8 from the below mentioned link: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
b. Install the downloaded executable file.
c. Set Environment Variable JAVA_HOME
i. Consider your JDK installation folder is: C:\Program Files\Java\jdk1.8.0_05
ii. Go to Start Menu and Type Edit the system environment variables
iii. Click on Environment Variables
iv. Under System variables click on New
v. Enter the following values:
Variable name: JAVA_HOME
Variable value: <JDK_INSTALLATION _FOLDER>
vi. Click OK
vii. Under System variables select Path variable and click Edit
viii. At the end of Path variable value append %JAVA_HOME%\bin;
ix. Click OK
d. To check Java has been installed correctly, open command prompt and type “java –version”.
#### 2.2.2 Maven Setup
Apache Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.
i. Download Apache Maven 3.3.9 from the below mentioned link: http://mirror.fibergrid.in/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip
ii. Extract the downloaded package inside directory of your choice e.g. C:\apache-maven-3.3.9
iii. Set Environment Variable MAVEN_HOME
1. Go to Start Menu and Type Edit the system environment variables
2. Click on Environment variables
3. Under System variables click New
4. Enter the following values:
Variable name: MAVEN_HOME
Variable value: <APACHE_MAVEN _FOLDER>
5. Click OK
6. Under System variables select Path variable and click Edit
7. At the end of Path variable value append %MAVEN_HOME%\bin;
8. To check maven has been installed correctly, open command prompt and type “mvn –version”.

#### 2.2.3 Eclipse Setup
Eclipse is an integrated development environment (IDE) and is written mostly in JAVA. Its primary use is for developing Java applications. Eclipse can be downloaded from below mentioned link:
 http://www.eclipse.org/downloads/packages/release/neon/3
Extract eclipse at any folder you want in your system.
Note: There is no need to install eclipse, you just need to run eclipse.exe whenever you want to open and use eclipse.

##### 2.2.3.1 JRE Settings
1. Windows -> Preferences -> Java -> installed JRE
2. Click on Add button
3. Click on Standard VM and Click Next
4. Browse the directory of your Java Installation Folder and Select JDK folder
5. Click Finish
6. Make sure you check the Java Installation you just added (You must select JDK not JRE)
7. Click Ok

#### 2.2.4 TestNG Setup
1. Help -> Eclipse Marketplace
Write “testng” in find box
Install “TestNG” for Eclipse.
Accept the License agreement and click on Finish.
2. To check TestNG has been installed correctly, open Eclipse marketplace again and search for “TestNG”. Now it will be displayed as installed.

#### 2.2.5 AspectJ Setup 
1. Help -> Eclipse Marketplace
Write “AspectJ compiler” in find box
Help -> Eclipse Marketplace
Write “AspectJ compiler” in find box
2. To check AspectJ Development Tools 2.2.4 has been installed correctly, open Eclipse marketplace again and search for “AspectJ Development Tools 2.2.4”. Now it will be displayed as installed.

### 2.3 Global Automation Framework Setup
1. Download global automation framework from Nagarro's GIT repository provided in the project's specifications.
2. Enter inside the Project Home Directory. Project Home Directory, is the Root Folder of our project which has the folder structure as shown below:
3. Inside Project Home Directory, open Command Prompt and type command:
```sh
mvn eclipse:eclipse
```
4. Message of Build Successful would display Message of Build Successful would display
5. Now, enter following command on Command Prompt to clean and build project:
mvn clean install. This will generate a JAR artifact for our project.
6. Message of Build Successful would display.
7. Now, open Eclipse
8. Go to File -> Import -> Maven -> Existing Maven Project
9. Browse the Source Code you configured in previous step.
10. Click Finish

## 3. Licensing
### 3.1 Terms and conditions for A2A.DRIVEN
* The license to use the A2A.DRIVEN by the Licensee will be free, included in the project delivery. Before installing the framework and dependencies, please ensure all relevant documents have been signed, otherwise its use strictly prohibited.
* A2A.DRIVEN is a protected asset of Nagarro and its sage is restricted: clients can only use it after signing of a usage agreement with Nagarro.

### 3.2 Licensing of A2A.DRIVEN dependencies and 3rd party software
Nagarro provides through A2A.DRIVEN framework a service, that includes support programs, code libraries and a scripting language among other software to help develop and glue together the testing components of the automation project.

We do not distribute binaries or code of our dependencies. Therefore, each dependency license and third-party software license used by the project team in conjunction with A2A.DRIVEN must be supported/agreed by the client. It is NOT Nagarro’s responsibility to ensure the license agreements between A2A.DRIVEN framework’s dependencies.

See all dependencies of the project listed under 4. List of Dependencies and Licenses.

## 4. List of Dependencies and Licenses
| Tool/Library | License |
| ------ | ------ |
| OWNER :: Core 1.0.4 (org.aeonbits.owner) | Copyright (c) 2012-2015, Luigi R. Viggiano THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.https://raw.github.com/lviggiano/owner/master/LICENSE |
| 1.Guava: Google Core Libraries For Java 16.0.1(com.google.guava); 2. Apache Commons Lang (org.apache.commons); 3. Commons IO(org.apache.commons); 4. Log4j SLF4J Impl (org.apache.logging.log4j); 5. Log4j Core (org.apache.logging.log4j); 6. Log4j API (org.apache.logging.log4j); 7.TestNg(org.testng); 8. Selenium-java (org.seleniumhq.selenium); 9. WebDriverManager (io.github.bonigarcia); 10. Selendroid Client (io.selendroid); 11. Selendroid Standalone (io.selendroid); 12. Okhttp (com.squareup.okhttp3); 13. Gson 2.3.1 (com.google.code.gson); 14.Jersey Core Client 2.25.1(org.glassfish.jersey.core); 15.Jersey Media JSON Jackson(org.glassfish.jersey.media); 16.Jersey Media JSON Processing(org.glassfish.jersey.media); 17.Jackson Databind (com.fasterxml.jackson.core); 18.Jackson Annotations(com.fasterxml.jackson.core); 19.Cucumber JVM Repackaged Dependencies(info.cukes) | Copyright 2019 Nagarro Software Pvt. Ltd. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. |
|1.AspectJ Runtime 1.7.3 (org.aspectj); 2. EclipseLink Moxy (org.eclipse.persistence)|This License is subject to the terms and conditions specified below: http://www.eclipse.org/legal/epl-v10.html All the modifications are also licensed under above terms and conditions SOURCE CODE IS PROVIDED UNDER THIS FILE |
|JAXB API (javax.xml.bind)| This License is subject to the terms at and conditions specified below: https://spdx.org/licenses/CDDL-1.1.html All the modifications are also licensed under above terms and conditions SOURCE CODE IS PROVIDED UNDER THIS FILE |
|ExtentReports 3.0.0 (com.aventstack) | https://opensource.org/licenses/BSD-3-Clause; This license has also been called the "New BSD License" or "Modified BSD License"; Copyright 2019 Nagarro Software Pvt. Ltd.; THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. |
|Base64 2.3.8 (net.iharder) | https://en.wikipedia.org/wiki/Public-domain-equivalent_license |
|Cucumber Reporting(net.masterthought)|www.gnu.org/licenses/lgpl-2.1.html|
