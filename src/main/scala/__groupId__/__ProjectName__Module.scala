package __groupId__

import org.eknet.publet.web.guice.{AbstractPubletModule, PubletModule, PubletBinding}

class __ProjectName__Module extends AbstractPubletModule with PubletBinding with PubletModule {

  def configure() {
    bind[__ProjectName__Setup].asEagerSingleton()
  }

  val name = "__ProjectName__"

  override val version = __groupId__.Reflect.version
  override val license = __groupId__.Reflect.licenses.headOption
}
