package ${groupId}

import com.google.inject.AbstractModule
import org.eknet.publet.web.guice.{PubletModule, PubletBinding}

class ${ProjectName}Module extends AbstractModule with PubletBinding with PubletModule {

  def configure() {
    binder.bindEagerly[${ProjectName}Setup]
  }

}
