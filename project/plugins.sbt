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

/** The best thing since sliced bread.
  *
  * //https://github.com/scalameta/sbt-scalafmt/releases
  */
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.2")

//=============================================================================
//=============================================================================

/** Used to create the convenient executable that allows us
  * to easily run the entire project from the command line.
  *
  * https://github.com/sbt/sbt-native-packager/releases
  */
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.8.1")

//=============================================================================
//=============================================================================

/** adds the sbt task headerCreateAll that adds license headers to files
  *
  * https://github.com/sbt/sbt-header/releases
  */
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "5.6.0")
