package controllers

import play._
import play.mvc._
import play.modules.gae._

trait Datas { self: Controller =>
  @Before
  def prepareData {
    session("user") match {
      case Some(name) => renderArgs.put("logined", true)
      case None => renderArgs.put("logined", false)
    }
  }
}

object Application extends Controller with Datas {

  def index = Template

  def login = GAE.login("Application.gaeLogined")

  def logout = GAE.logout("Application.index")

  def gaeLogined = if (GAE.isLoggedIn) {
    session.put("user", GAE.getUser.getEmail)
    Action(Application.index)
  } else {
    Action(Application.login)
  }

}
