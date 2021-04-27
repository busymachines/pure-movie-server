/*
 * Copyright 2021 BusyMachines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package phms.config

import phms.*

/** Capability for reading config files.
  *
  * Used to signal that something in our app
  * reads configurations
  */
sealed trait Config[F[_]] {
  protected given async: Async[F]
  def load[T](value: ConfigValue[F, T]): F[T] = value.load[F]
}

object Config {

  def resource[F[_]](using F: Async[F]): Resource[F, Config[F]] =
    new Config[F] {
      override protected given async: Async[F] = F
    }.pure[Resource[F, *]]
}
