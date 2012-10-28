package __groupId__

import com.google.inject.AbstractModule
import org.eknet.publet.web.guice.{PubletModule, PubletBinding}

class __ProjectName__Module extends AbstractModule with PubletBinding with PubletModule {

  def configure() {
    binder.bindEagerly[__ProjectName__Setup]
  }

}
