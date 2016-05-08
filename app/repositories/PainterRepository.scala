package repositories

import javax.inject.Inject

import models.Painter
import play.api.db.{Database, _}

/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/7/16.
  */
trait PainterRepository {
  def find (id: Int) :Painter
  def upsert (painter:Painter) : Painter
  def insert (painter: Painter) : Painter
  def delete (painter: Painter) : Boolean
}
class PainterRepositoryImpl @Inject()(@NamedDatabase("default") database:Database) extends PainterRepository {
  override def find(id: Int): Painter = {
    val c = database.getConnection()
    val s = c.createStatement()
    val rs = s.executeQuery("select count(*) P from painters ")
    var count = -1
    if (rs.next()){
      count = rs.getInt(1)
    }
    rs.close()
    s.close()
    c.close()
    Painter(count, "Vincent", "Van Gogh", "VVG", "https://upload.wikimedia.org/wikipedia/commons/d/df/Van_Gogh_self_portrait_as_an_artist.jpg")
  }

  override def insert(painter: Painter): Painter = {null}

  override def delete(painter: Painter): Boolean = {false}

  override def upsert(painter: Painter): Painter = {null}
}
