Concept Explorer 1.2 Readme File
================================

What is it?
-----------
This is a version 1.2 of "Concept Explorer"(ConExp) tool,
that implements basic functionality needed for study and
research of Formal Concept Analysis.
For more information about Formal Concept Analysis, see
http://www.math.tu-dresden.de/~ganter/fba.html and
http://www.fcahome.org.uk/

It is released under BSD-style license. Please read file license.txt
in the distribution. You can read license files of libraries, that are
used in ConExp, reading file, containing "license" in its name and first
name of which corresponds to the name of the library jar file.

What can I do with ConExp?
--------------------------
In ConExp the following functionality is implemented:
-context editing
-building concept lattices from context
-finding bases of implications that are true in context
-finding bases of association rules that are true in context
-performing attribute exploration


What do I need to run ConExp?
-----------------------------
You need a Java Runtime Environment (JRE) version 1.3 or higher (1.4.1_01 recommended).
For the development Java Development Kit (JDK) version 1.4.1_01 is usually used.
If you don't have it, you can get them from the following URL:
http://java.sun.com/j2se/downloads.html

How to start it?
----------------
Run the appropriate start script("conexp.bat" on Windows, "conexp.sh" on Unix).
On Unix: before that set the executable attribute for "conexp.sh". If you run it
from the command line, make sure, that you are in the installation directory.
Alternatively, you can run "java -jar conexp.jar" from the command line

On Windows: use "javaw -jar conexp.jar" if you don't want to see
the java console.


Where I can get help?
---------------------
The best place is ConExp user list: conexp-user@lists.sourceforge.net

How can I help?
---------------
First of all: give feedback. Comment, complain, ask questions.
Put comments and new items into the tracker system. Tell about
features you would like to have in ConExp and what you
miss to do them.

You can follow the development by getting the latest version
from the CVS repository:
  http://sourceforge.net/cvs/?group_id=72262

The CVS contains the source, all libraries needed, a build
script for usage with Apache Ant (http://ant.apache.org/) and some other useful
files.

How did ConExp appeared?
------------------------
ConExp was first developed as a part of master's thesis in National Technical
University of Ukraine "KPI" in 2000. During the following years, it was extended
and now is an open source project on Sourceforge.


Enjoy!
        The ConExp Team.