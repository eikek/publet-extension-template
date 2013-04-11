package __groupId__

import org.eknet.publet.web.guice.{AbstractPubletModule, PubletModule, PubletBinding}
import org.eknet.publet.vfs.Resource

class __ProjectName__Module extends AbstractPubletModule with PubletBinding with PubletModule {

  def configure() {
    bind[__ProjectName__Setup].asEagerSingleton()

    bindDocumentation(List(
      doc("__ProjectName__.md"))
    )
  }

  private[this] def doc(name: String) = Resource.classpath("__groupIdPath__/doc/"+ name)

  val name = "__ProjectName__"

  override val version = __groupId__.Reflect.version
  override val license = __groupId__.Reflect.licenses.headOption
}
