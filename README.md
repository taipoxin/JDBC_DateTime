# JDBC_DateTime

This console/chart application created as my first JDBC project.
It make nice interface for Databases, and you can edit my project for your own table on SQL.
My project works with MySQL local table on Database STATS. Table called DateIter and need to 
store and represent Dates and Iterations of the your work. For example, today you worked 6 iterations(about 3 hours)
and you add to your table Dateiter new row using the Application. 
Of course, you can add row through Oracle, MySQL Monitor etc.
Main feature of my App is convinient representation of your table. 
You can choose any month(or all months) and App create BarChart for this Data (Date-Iters).

Project created for self-using, but now I say "Why not?" and present it on GitHub.

        How to use?
1.For default using, you should have MySQL Connector, write it in CLASSPATH variable like this: (in Linux Mint) 

  # /etc/profile: system-wide .profile file for the Bourne shell (sh(1))
  # and Bourne compatible shells (bash(1), ksh(1), ash(1), ...).

  CLASSPATH=/usr/lib/java/mysql-connector-java-5.1.39   // your own path

  PATH=$PATH:$HOME:$JAVA_HOME/bin:$CLASSPATH
  export JAVA_HOME
  export CLASSPATH
  export PATH

Than you need create Database STATS and Table DateIter within it.

  Table should have two columns: 'Date' and 'Iters':

  `Date` varchar(10),
  `Iters` int(10) unsigned

  of course, unsigned is optional.
  
  Thats all! Print you Login and Pass and use Application
