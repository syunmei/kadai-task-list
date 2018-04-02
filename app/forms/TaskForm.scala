package forms

case class TaskForm(id: Option[Long], status: String, content: String)