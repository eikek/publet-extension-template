package __groupId__

import org.eknet.publet.web.guice.{PubletModule, PubletBinding}
import org.eknet.guice.squire.SquireModule
import org.eknet.publet.reflect.Reflect

class __ProjectName__Module extends SquireModule with PubletBinding with PubletModule {

  def configure() {
    bind[__ProjectName__Setup].asEagerSingleton()
  }

  def name = "__ProjectName__"

  def version = Reflect.version
}
