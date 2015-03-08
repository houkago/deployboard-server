package deployboard

/**
 *
 * @author stormcat24
 */
package object exception {

  class DeployboardException(message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

  class BadRequestException(message: String = null, cause: Throwable = null) extends DeployboardException(message, cause)

  class UnauthorizedException(message: String) extends DeployboardException(message)

}
