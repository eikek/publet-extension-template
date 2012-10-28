//
// Simple script that renames the project skeleton
// according to your input. Needs JDK7


import io.Source
import java.io._
import java.nio.file._
import attribute.BasicFileAttributes
import scala.collection.JavaConversions._

def camelCase(str: String) = {
  val w = "([\\w]+)".r
  val s = "([^\\w]+)".r
  val text = w.replaceAllIn(str, m => m.matched.toLowerCase.capitalize)
  s.replaceAllIn(text, "")
}

val source = Source.stdin

print("groupId: ")
val groupId = source.getLines().toStream(0)
val groupIdPath = groupId.replace(".", File.separator)

print("projectId: ")
val projectId = source.getLines().toStream(0)
val cProjectId = camelCase(projectId)

def filter(f: File) {
  val temp = File.createTempFile(f.getName, "")
  val out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp), "UTF-8"))
  Source.fromFile(f, "UTF-8").getLines() foreach { line =>
    val repl = line.replace("__groupId__", groupId)
      .replace("__groupIdPath__", groupIdPath)
      .replace("__ProjectName__", cProjectId)
      .replace("sbt__projectId__", projectId)
      .replace("__projectId__", projectId)
    out.write(repl+"\n")
  }
  out.close()
  Files.move(temp.toPath, f.toPath, StandardCopyOption.REPLACE_EXISTING)
}

val workingDir = FileSystems.getDefault.getPath("")
val filterVisitor = new SimpleFileVisitor[Path]() {

  override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes) = {
    if (dir.toString.startsWith("."))
      FileVisitResult.SKIP_SUBTREE
    else
    if (dir.iterator().map(_.toString).toSet.contains("target"))
      FileVisitResult.SKIP_SUBTREE
    else
      FileVisitResult.CONTINUE
  }

  override def visitFile(file: Path, attrs: BasicFileAttributes) = {
    if (file.toString != "install.scala") {
      println("Filtering: "+ file)
      filter(file.toFile)
    }
    super.visitFile(file, attrs)
  }
}

val renameVisitor = new SimpleFileVisitor[Path]() {

  override def postVisitDirectory(dir: Path, exc: java.io.IOException) = {
    if (dir.endsWith("__groupId__")) {
      val np = dir.resolveSibling(groupIdPath)
      Files.createDirectories(np.getParent)
      Files.move(dir, np)
    }
    super.postVisitDirectory(dir, exc)
  }
  override def visitFile(file: Path, attrs: BasicFileAttributes) = {
    val name = file.getFileName
    if (name.toString.contains("__projectId__")) {
      Files.move(file, file.resolveSibling(name.toString.replace("__projectId__", projectId)))
    }
    if (name.toString.contains("__ProjectName__")) {
      Files.move(file, file.resolveSibling(name.toString.replace("__ProjectName__", cProjectId)))
    }
    FileVisitResult.CONTINUE
  } 
}

Files.walkFileTree(workingDir, filterVisitor)
Files.walkFileTree(workingDir, renameVisitor)

println("\nDone.")
println("Remove the src/install.scala script and the .git/ directory. Then you")
println("can start sbt and compile the project.\n")

