//
// Simple script that renames the project skeleton
// according to your input.


import io.Source
import java.io._
import java.nio.file._
import attribute.BasicFileAttributes
import scala.collection.JavaConversions._

val source = Source.stdin

print("groupId: ")
val groupId = source.getLines().toStream(0)
val groupIdPath = groupId.replaceAll("\\.", File.separator)

print("projectId: ")
val projectId = source.getLines().toStream(0)


def filter(f: File) {
  val temp = File.createTempFile(f.getName, "")
  val out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp), "UTF-8"))
  Source.fromFile(f, "UTF-8").getLines() foreach { line =>
    val repl = line.replace("${groupId}", groupId)
      .replace("${groupIdPath}", groupIdPath)
      .replace("${projectId}", projectId)
    out.write(repl+"\n")
  }
  out.close()
  Files.move(temp.toPath, f.toPath, StandardCopyOption.REPLACE_EXISTING)
}

Files.walkFileTree(FileSystems.getDefault.getPath(""), new SimpleFileVisitor[Path]() {

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
      println(file)
      filter(file.toFile)
    }
    super.visitFile(file, attrs)
  }
})
