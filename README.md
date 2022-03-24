# CS4227-Restaurant Application


## Names and ID numbers
Michele Cavaliere - 18219365

Nicole Berty - 18246702

Sean Lynch - 18245137

Matt Lucey - 18247083

## How to run via CMD / Terminal

To compile and run the program:
1. Open CMD / terminal
2. Change directory to projectLocation\CS4227-Application\
3. Do one of the following:
   1. If Perl is installed, regardless of OS, use the following command: *perl convertToCmdFormat.pl* (if on Linux/MacOS and you get a permission error, use the command *sudo perl convertToCmdFormat.pl*)
   2. If on Windows, use the following command: *.\convertToCmdFormatWin.exe*
   3. If on Linux, first make the convertToCmdFormatLinux file executable by right-clicking on it, selecting properties, and enabling the "Allow executing file as program" option. Then use the following command: 
   *sudo ./convertToCmdFormatLinux*
4. Type this command: *cd cmdSrc*
5. Type this command to compile the program: *javac Main.java*
6. Type this command to run the program: *java Main*

To run the program using previously compiled files from IDE:

*It's not possible to compile the program via command line as it was made in an IDE, hence the need for the Perl script described above - the below will allow you to run it but won't work with programs such as SonarQube*
1. Go to projectLocation\CS4227-Application\out\production\cs4125-restaurant-project
2. Run command: *java com.company.Main*

Alternatively open the project using an IDE such as Intellij or Visual Studio Code to compile and run the project.
