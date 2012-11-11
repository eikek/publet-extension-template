package __groupId__

import com.google.inject.AbstractModule
import org.eknet.publet.web.guice.{PubletModule, PubletBinding}
import org.eknet.guice.squire.SquireModule

class __ProjectName__Module extends SquireModule with PubletBinding with PubletModule {

  def configure() {
    bind[__ProjectName__Setup].asEagerSingleton()
  }

}
