package phms.server.config

import com.comcast.ip4s.{Host, Port}

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 20 Jun 2018
  */
final case class HttpConfig(
  port:      Port,
  host:      Host,
  apiRoot:   APIRoot,
  bootstrap: Boolean,
)