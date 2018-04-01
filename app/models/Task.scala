package models

import java.time.ZonedDateTime

import scalikejdbc._, jsr310._
import skinny.orm._

/**
  * Task
  */
case class Task(id: Option[Long], content: String, createAt: ZonedDateTime, updateAt: ZonedDateTime)

object Task extends SkinnyCRUDMapper[Task] {

  override def tableName = "tasks"

  override def defaultAlias: Alias[Task] = createAlias("m")

  override def extract(rs: WrappedResultSet, n: ResultName[Task]): Task =
    autoConstruct(rs, n)

  private def toNamedValues(record: Task): Seq[(Symbol, Any)] = Seq(
    'content  -> record.content,
    'createAt -> record.createAt,
    'updateAt -> record.updateAt
  )

  def create(tasks: Task)(implicit session: DBSession): Long =
    createWithAttributes(toNamedValues(tasks): _*)

  def update(tasks: Task)(implicit session: DBSession): Int =
    updateById(tasks.id.get).withAttributes(toNamedValues(tasks): _*)

}
