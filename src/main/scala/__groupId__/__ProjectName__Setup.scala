package __groupId__

import org.eknet.publet.web.guice.PubletStartedEvent
import com.google.inject.{Inject, Singleton}
import com.google.common.eventbus.Subscribe
import org.eknet.publet.Publet
import org.eknet.publet.web.template.DefaultLayout
import org.eknet.publet.web.asset.{Group, AssetManager, AssetCollection}
import org.eknet.publet.vfs.util.ClasspathContainer
import org.eknet.publet.vfs.Path

@Singleton
class __ProjectName__Setup @Inject() (publet: Publet, assetMgr: AssetManager) extends AssetCollection {

  override def classPathBase = "/${groupIdPath}/includes"

  @Subscribe
  def mountResources(event: PubletStartedEvent) {

    val myAssets = Group("${projectId}.assets")
      .add(resource("css/mytheme.css"))
      .require(DefaultLayout.Assets.bootstrap.name)

    assetMgr setup (myAssets)
   
    assetMgr setup
      Group("default").use(myAssets.name)

    val cont = new ClasspathContainer(base = "/${groupIdPath}/includes/templ/")
    publet.mountManager.mount(Path("/publet/${projectId}"), cont)
  }
}
