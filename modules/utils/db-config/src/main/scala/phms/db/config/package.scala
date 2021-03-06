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

package phms.db

import phms._
import com.comcast.ip4s._

package object config {
  final object DBHost extends SproutSub[Host]
  final type DBHost = DBHost.Type

  final object DBPort extends SproutSub[Port]
  final type DBPort = DBPort.Type

  final object DBUsername extends SproutSub[String]
  final type DBUsername = DBUsername.Type

  type JDBCUrl = JDBCUrl.Type

  final object JDBCUrl extends SproutSub[String] {

    def postgresql(host: DBHost, port: DBPort, db: DatabaseName): this.Type =
      this.apply(s"jdbc:postgresql://$host:$port/$db")

    def postgresql(host: DBHost, port: DBPort, db: DatabaseName, schema: SchemaName): this.Type =
      this.apply(s"jdbc:postgresql://$host:$port/$db?currentSchema=$schema")
  }

  final object DBPassword extends SproutSub[String]
  final type DBPassword = DBPassword.Type

  final object TableName extends SproutSub[String]
  final type TableName = TableName.Type

  final object DatabaseName extends SproutSub[String]
  final type DatabaseName = DatabaseName.Type

  final object SchemaName extends SproutSub[String] {
    def public: SchemaName = SchemaName("public")
  }
  final type SchemaName = SchemaName.Type

  object MigrationLocation extends Sprout[String] {
    /** The default location of flyway migrations
      */
    def default: this.Type = this.apply("db/migration")
  }
  type MigrationLocation = MigrationLocation.Type

  object IgnoreMissingMigrations extends SproutSub[Boolean] {
    val False: this.Type = this.apply(false)
    val True:  this.Type = this.apply(true)
  }
  type IgnoreMissingMigrations = IgnoreMissingMigrations.Type

  object CleanOnValidationError extends SproutSub[Boolean] {
    val False: this.Type = this.apply(false)
    val True:  this.Type = this.apply(true)
  }
  type CleanOnValidationError = CleanOnValidationError.Type
}
