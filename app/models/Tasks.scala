package models

import java.time.ZonedDateTime

import scalikejdbc._
import skinny.orm._

/**
  * Tasks
  */
case class Tasks(id: Option[Long], content: String, createAt: ZonedDateTime, updateAt: ZonedDateTime)

object Tasks extends SkinnyCRUDMapper[Tasks] {

  override def tableName = "tasks"

  override def defaultAlias: Alias[Tasks] = createAlias("m")

  override def extract(rs: WrappedResultSet, n: ResultName[Tasks]): Tasks =
    autoConstruct(rs, n)

  private def toNamedValues(record: Tasks): Seq[(Symbol, Any)] = Seq(
    'content     -> record.content,
    'createAt -> record.createAt,
    'updateAt -> record.updateAt
  )

  def create(tasks: Tasks)(implicit session: DBSession): Long =
    createWithAttributes(toNamedValues(tasks): _*)

  def update(tasks: Tasks)(implicit session: DBSession): Int =
    updateById(tasks.id.get).withAttributes(toNamedValues(tasks): _*)

}
