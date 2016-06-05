package repositories

import models.Painter
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}

/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 6/4/16.
  */
class PainterRepositorySpec() extends PlaySpec with OneAppPerSuite {
  val vvg: Painter = Painter(1, "Vincent", "van Gogh", "The Little Painter Fellow", None, None, "https://upload.wikimedia.org/wikipedia/commons/d/df/Van_Gogh_self_portrait_as_an_artist.jpg", 0)
  val repo: PainterRepository = app.injector.instanceOf[PainterRepository]
  "Painter Repository " should {
    "be injected " in {
      repo must not be null
    }
  }
  it should {
    "pull up a painter" in {
      repo.find(1).get must be(vvg)
    }
  }
}
