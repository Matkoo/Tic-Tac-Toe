package hu.matko.tictactoe.android.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

/**
 * @author Matkovics Gergely<br></br>
 * E-mail: [gergelymatkovics82@gmail.com](mailto:gergelymatkovics82@gmail.com)
 */

object Game : IntIdTable("game_data"){

    var gameDate: Column<String> = varchar("game_date",255)
    var winner: Column<String> = varchar("winner",255)
    var gamePos: Column<String> = varchar("game_position",255)

}

class GameEntity (id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<GameEntity>(Game)

    var gameDate by Game.gameDate
    var winner by Game.winner
    var gamePos by Game.gamePos

}