package ${groupId}

import org.eknet.publet.web.guice.PubletStartedEvent
import com.google.inject.{Inject, Singleton}
import org.eknet.publet.Publet
import org.eknet.publet.web.asset.{Group, AssetManager, AssetCollection}
import org.eknet.publet.vfs.util.ClasspathContainer
import org.eknet.publet.vfs.Path

@Singleton
class PhotoGallerySetup @Inject() (publet: Publet, assetMgr: AssetManager) extends AssetCollection {

  override def classPathBase = "${groupIdPath}/includes"

  def mountResources(event: PubletStartedEvent) {

    val myAssets = Group("${projectId}")
      .add(resource("mytheme.css"))

    assetMgr setup (myAssets)
   
    assetMgr setup
      Group("default").use(myAssets.name)

    val cont = new ClasspathContainer(base = "${groupIdPath}/includes/templ/")
    publet.mountManager.mount(Path(""), cont)
  }
}
