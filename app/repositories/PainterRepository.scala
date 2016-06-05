package repositories

import javax.inject.Inject

import anorm._
import models.Painter
import play.api.db.{Database, _}

/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/7/16.
  */
trait PainterRepository {
  def namedParameters(painter: Painter): List[NamedParameter] = {
    import anorm.NamedParameter.symbol
    import painter._
    List('painterId -> painterId, 'fn -> firstName, 'ln -> lastName, 'psn -> pseudonym, 'bd -> birthDate, 'dd -> dateOfDeath, 'pict -> picture, 'version -> version)
  }

  def find(id: Long): Option[Painter]

  def save(painter: Painter): Option[Painter]

  def insert(painter: Painter): Option[Painter]

  def delete(painter: Painter): Boolean

  implicit def  painterToMapEntry (p:Painter) : (Long, Painter) = {
    p.painterId -> p
  }

  implicit def painterToId(p: Painter): Long = {
    p.painterId
  }

}

class PainterRepositoryImpl @Inject()(@NamedDatabase("default") database: Database) extends PainterRepository {
  override def find(id: Long): Option[Painter] = {
    database.withConnection { implicit c =>
      SQL(
        """
          select * from painters where painter_id = {id}
        """) on ('id -> 1) as parser.* headOption
    }
  }

  def parser = Macro.parser[Painter](
    "PAINTER_ID",
    "FIRST_NAME",
    "LAST_NAME",
    "PSEUDONYM",
    "BIRTH_DATE",
    "DATE_OF_DEATH",
    "PICTURE",
    "VERSION"
  )

  override def insert(painter: Painter):Option[Painter] = {
      database.withConnection { implicit c =>
        val id:Option[Long]= SQL(
          s"""
             |insert into Painters(
             |    SEQ_PAINTERS.nextval,
             |    FIRST_NAME,
             |    LAST_NAME,
             |    PSEUDONYM,
             |    BIRTH_DATE"
             |    DATE_OF_DEATH,
             |    PICTURE,
             |    VERSION
             |   ) values (
             |    SEQ_PAINTERS.nextval,
             |    {fn},
             |    {ln},
             |    {psn},
             |    {bd},
             |    {dd},
             |    {picture},
             |    {version})
             |    """).on(namedParameters(painter):_*).executeInsert()
        Option(Painter(id.get, painter.firstName, painter.lastName, painter.pseudonym, None, None, painter.picture, painter.version))
      }
  }

  override def delete(painter: Painter): Boolean = {
    false
  }

  override def save(painter: Painter): Option[Painter] = {
    None
  }
}
