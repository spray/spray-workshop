Step 0 - Setting up the environment
-----------------------------------

 * Install git
 * Clone example project:
```
git clone git://github.com/spray/spray-workshop.git
```

 * Install sbt. See http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html
 * Install IntelliJ (download from https://www.jetbrains.com/idea/download/)
 * Setup sbt-idea plugin globally:
   Add this code to your `~/.sbt/0.13/plugins/plugins.sbt` file:
```scala
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.1")
```
 * Start `sbt` in the project directory
 * Type `gen-idea` to generate idea project
 * Run `re-start` in sbt to startup the server
 * Verify that you can access the server from your browser
 * Change the port the server runs on and `re-start` the server and
   check that everything still works

Tips and tricks
---------------

 * To open the current project in idea from the command line, you can add this script to your path
```sh
IDEA_HOME=~/opt/idea-IU-129.161
JDK_HOME=/usr/lib/jvm/java-6-sun
$IDEA_HOME/bin/idea.sh `pwd`
```
