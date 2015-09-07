# Triage
Android application for nurses and physicians.

![ScreenShot](https://raw.github.com/rathukugan/Triage/master/screenshots/Triage Home Screen.png)

#### Building project
* Import project into android studio or eclipse, and run project to simulate the android app.

#### Screenshots

![ScreenShot](https://raw.github.com/rathukugan/Triage/master/screenshots/New Patient.png) ![ScreenShot](https://raw.github.com/rathukugan/Triage/master/screenshots/Patient View.png)

#### Features
1. Nurses and physicians can launch the triage application and log in using a username and password, which loads saved data, if it exists. In our, unrealistic, implementation, all username passwords are stored in the file passwords.txt that we give you.
2. Nurses can create new patient records and record individual patient data (name, birth date, and health card number)
3. Nurses can record the date and time when a patient has been seen by a doctor.
4. Nurses can access a list of patients (name, birth date and health card number) who have not yet been seen by a doctor categorized and ordered by decreasing urgency according to hospital policy.
5. Using the health card number, physicians/nurses can look up a patient's record, which contains all data recorded about that patient.
6. Physicians can record prescription information (name of the medication and instructions) for a given patient.
7. Nurses can update a patient's visit record with vital signs (temperature, blood pressure, and heart rate) at a particular time, retaining older values.

#### Credentials
Nurses and Doctor credentials are stored in a MySql database.

To pass the home screen, login using one of the following usernames in the database (user, password, type):

* yorgos,34234dd,nurse
* timesnew,23423f,nurse
* michalis,de34x,nurse
* calibri,ff222,nurse
* kokos,ooooo,nurse
* zak,dfgreew21,nurse
* janice,o323l1,nurse
* emad,emad,physician
* huwa,234fe,physician
* expo,v2341d,physician
* lam,xwe23,physician
* joseph,rw2323,physician
