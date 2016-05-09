package repositories

import javax.inject.Inject
import anorm._
import models.Painter
import play.api.db.{Database, _}

/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/7/16.
  */
trait PainterRepository {
  def find(id: Int): Painter

  def upsert(painter: Painter): Painter

  def insert(painter: Painter): Painter

  def delete(painter: Painter): Boolean
}

class PainterRepositoryImpl @Inject()(@NamedDatabase("default") database: Database) extends PainterRepository {

  override def find(id: Int): Painter = {
    database.withConnection { implicit c =>
      val result = SQL(
        s"""
          select * from painters where painter_id = ${id}
        """) as Painter.parser.*
      result(0);
    }
  }

  override def insert(painter: Painter): Painter = {

      database.withConnection { implicit c =>
        val id: Option[Long] = SQL(
          s"""
             insert into Painters values (
               SEQ_PAINTERS.nextval,
               '${painter.firstName}',
               '${painter.lastName}',
               '${painter.pseudonym}',
               null,
               null,
               '${painter.picture}',
               ${painter.version})
             """)
          .executeInsert()
        Painter(id.get, painter.firstName, painter.lastName, painter.pseudonym, painter.picture, painter.version)
      }
  }

  override def delete(painter: Painter): Boolean = {
    false
  }

  override def upsert(painter: Painter): Painter = {
    null
  }
}
