package deployboard

import com.twitter.finatra.Controller
import scaldi.{Injector, Injectable}

/**
 * base controller
 * @author stormcat24
 */
trait DeployboardController extends Controller with Injectable {

  implicit val injector: Injector

}
