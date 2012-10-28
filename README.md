# extension skeleton

This is an extension skeleton for [publet](https://eknet.org/main/projects/publet/index.html).

Clone this project

    git clone https://eknet.org/git/eike/publet-extension-skeleton.git

and then execute the `install.scala` script

    scala src/install.scala

This will ask for a `groupId` and `projectId` and then renames and filters
the files. After that, just remove the `intall.scala` script and start 
[sbt](https://github.com/harrah/xsbt). The script needs JDK7 to run.

The [publet-sbt-plugin](https://eknet.org/gitr/?r=publet-sbt-plugin.git) is 
configured and you can start publet with the new extension using `publet:start`
sbt task.
