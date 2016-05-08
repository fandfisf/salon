package repositories

import javax.inject.{Inject, Singleton}

import play.api.db.{Database, NamedDatabase}
import utils.CollectionEnhancements._
/**
  * Created by Prashant S Khanwale @ Suveda LLC  on 5/6/16.
  */
case class Employee ( id:Int, firstName:String, lastName:String);
trait EmployeeRepository{
  implicit def  employeeToMapEntry (e:Employee) : (Int, Employee) = {
    e.id -> e
  }
  implicit def  employeeToId (e:Employee) : Int = {
    e.id
  }

  def find ():List [Employee]
  def add (employee:Employee):Boolean
  def delete (employee: Employee):Boolean
}
@Singleton
class EmployeeRepositoryImpl  @Inject() (@NamedDatabase("default") database:Database) extends EmployeeRepository{


  var employees :Map[Int, Employee] = Map(
    1 -> Employee (1, "Prashant", "Khanwale")
    , 2 -> Employee (2, "John", "Smith")
    , 3 -> Employee (3, "Jane", "Doe")
  )

  def find ():List [Employee] = {
    if (database == null ) throw new IllegalStateException("Database was not initialized")
    val rs = database.getConnection().createStatement()
      .executeQuery("select count(*) c from CATEGORIES")
    rs.close()
    employees.values.toList
  }

  override def add(employee: Employee):Boolean = {
    if (!(employees?employee)) {
      employees = employees + employee
    }
    employees?employee
  }

  override def delete (employee: Employee):Boolean = {
    if (employees ? employee) {
      employees -= employee
      true
    }else{
      false
    }
  }
}
